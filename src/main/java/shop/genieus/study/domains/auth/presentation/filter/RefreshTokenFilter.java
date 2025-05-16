package shop.genieus.study.domains.auth.presentation.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import shop.genieus.study.domains.auth.application.AuthenticationService;
import shop.genieus.study.domains.auth.application.AuthorizationService;
import shop.genieus.study.domains.auth.application.dto.result.ReIssueTokenResult;
import shop.genieus.study.domains.auth.application.dto.result.TokenFailCode;
import shop.genieus.study.domains.auth.domain.vo.TokenPair;
import shop.genieus.study.domains.auth.presentation.dto.CustomPrincipal;
import shop.genieus.study.domains.auth.presentation.dto.request.ReIssueTokenRequest;
import shop.genieus.study.domains.auth.presentation.dto.response.RefreshTokenResponse;
import shop.genieus.study.domains.auth.presentation.utils.AuthResponseSender;

@Slf4j
@Component
@RequiredArgsConstructor
public class RefreshTokenFilter extends OncePerRequestFilter {
  private static final String REFRESH_URI = "/auth/refresh-token";
  private final AuthenticationService authenticationService;
  private final AuthorizationService authorizationService;
  private final AuthResponseSender authResponseSender;
  private final ObjectMapper objectMapper;

  @Override
  protected void doFilterInternal(
      HttpServletRequest httpRequest, HttpServletResponse httpResponse, FilterChain filterChain)
      throws ServletException, IOException {

    if (!httpRequest.getRequestURI().equals(REFRESH_URI)) {
      filterChain.doFilter(httpRequest, httpResponse);
      return;
    }

    try {
      ReIssueTokenRequest refreshRequest =
          objectMapper.readValue(httpRequest.getInputStream(), ReIssueTokenRequest.class);

      ReIssueTokenResult result =
          authenticationService.reIssueTokenPair(
              refreshRequest.accessToken(), refreshRequest.refreshToken());

      if (!result.success()) {
        TokenFailCode failCode = TokenFailCode.EXPIRE_REFRESH;
        Map<String, String> details = new HashMap<>(Map.of("code", failCode.getCode()));
        unsuccessfulAuthentication(httpRequest, httpResponse, failCode.getMessage(), details);
        return;
      }

      successfulAuthentication(httpRequest, httpResponse, result);
    } catch (Exception e) {
      unsuccessfulAuthentication(httpRequest, httpResponse, e, null);
    }
  }

  protected void successfulAuthentication(
      HttpServletRequest httpRequest, HttpServletResponse httpResponse, ReIssueTokenResult result) {
    try {

      CustomPrincipal principal = authorizationService.getPrincipal(result.userId());

      RefreshTokenResponse response = createResponse(result.tokenPair(), principal);
      authResponseSender.sendSuccessResponse(httpResponse, response);

      log.info("리프레쉬 토큰 발급 성공 user: id-{}", result.userId());
    } catch (AuthenticationException authException) {
      log.error("AuthenticationException 오류: ", authException);
      throw authException;
    } catch (Exception e) {
      log.error("인증 중 오류 발생: ", e);
      throw e;
    }
  }

  protected void unsuccessfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      Exception exception,
      Map<String, String> details) {
    log.error("리프레쉬 토큰 발급 중 오류 발생: ", exception);
    authResponseSender.sendErrorResponse(
        request, response, HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage(), details);
  }

  protected void unsuccessfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      String message,
      Map<String, String> details) {
    log.error("리프레쉬 토큰 발급 중 오류 발생: ", message);
    authResponseSender.sendErrorResponse(
        request, response, HttpServletResponse.SC_UNAUTHORIZED, message, details);
  }

  private RefreshTokenResponse createResponse(TokenPair tokenPair, CustomPrincipal principal) {
    return new RefreshTokenResponse(
        true,
        tokenPair.getAccessTokenCredential().tokenValue(),
        tokenPair.getRefreshTokenCredential().tokenValue());
  }
}
