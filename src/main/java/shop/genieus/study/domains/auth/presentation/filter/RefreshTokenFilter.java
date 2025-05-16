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
import shop.genieus.study.commons.util.LoggingUtil;
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
  protected boolean shouldNotFilter(HttpServletRequest request) {
    return !request.getRequestURI().equals(REFRESH_URI);
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest httpRequest, HttpServletResponse httpResponse, FilterChain filterChain)
      throws ServletException, IOException {

    try {
      ReIssueTokenRequest refreshRequest =
          objectMapper.readValue(httpRequest.getInputStream(), ReIssueTokenRequest.class);

      if (log.isDebugEnabled()) {
        log.debug("토큰 갱신 요청 - IP: {}", LoggingUtil.getClientIp(httpRequest));
      }

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
      log.warn(
          "토큰 갱신 요청 처리 중 오류: {}, IP: {}", e.getMessage(), LoggingUtil.getClientIp(httpRequest));
      unsuccessfulAuthentication(httpRequest, httpResponse, e, null);
    }
  }

  protected void successfulAuthentication(
      HttpServletRequest httpRequest, HttpServletResponse httpResponse, ReIssueTokenResult result) {
    try {
      CustomPrincipal principal = authorizationService.getPrincipal(result.userId());

      RefreshTokenResponse response = createResponse(result.tokenPair(), principal);
      authResponseSender.sendSuccessResponse(httpResponse, response);

      log.info("토큰 갱신 성공 - 사용자: {}, IP: {}", result.userId(), LoggingUtil.getClientIp(httpRequest));
    } catch (AuthenticationException authException) {
      log.error("토큰 갱신 후 인증 정보 조회 오류: {}", authException.getMessage());
      throw authException;
    } catch (Exception e) {
      log.error("토큰 갱신 후 응답 생성 중 오류: {}", e.getMessage());
      throw e;
    }
  }

  protected void unsuccessfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      Exception exception,
      Map<String, String> details) {
    log.warn("토큰 갱신 실패 - 원인: {}, IP: {}", exception.getMessage(), LoggingUtil.getClientIp(request));
    authResponseSender.sendErrorResponse(
        request, response, HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage(), details);
  }

  protected void unsuccessfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      String message,
      Map<String, String> details) {
    log.warn("토큰 갱신 실패 - 메시지: {}, IP: {}", message, LoggingUtil.getClientIp(request));
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
