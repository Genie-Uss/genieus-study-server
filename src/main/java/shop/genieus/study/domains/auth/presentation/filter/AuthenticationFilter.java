package shop.genieus.study.domains.auth.presentation.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import shop.genieus.study.domains.auth.application.AuthenticationService;
import shop.genieus.study.domains.auth.domain.vo.TokenPair;
import shop.genieus.study.domains.auth.presentation.dto.CustomPrincipal;
import shop.genieus.study.domains.auth.presentation.dto.request.LoginRequest;
import shop.genieus.study.domains.auth.presentation.dto.response.LoginResponse;
import shop.genieus.study.domains.auth.presentation.utils.AuthResponseSender;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private final AuthenticationService authenticationService;
  private final AuthResponseSender authResponseSender;
  private final ObjectMapper objectMapper;

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    try {
      LoginRequest loginRequest =
          objectMapper.readValue(request.getInputStream(), LoginRequest.class);

      CustomPrincipal principal =
          authenticationService.getUserByCredentialInfo(loginRequest.toInfo());

      return new UsernamePasswordAuthenticationToken(
          principal, null, extractAuthorities(principal.role()));
    } catch (IOException e) {
      log.error("[IOException] 인증 실패: ", e);
      throw new AuthenticationException("로그인에 실패했습니다.") {};
    }
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest httpRequest,
      HttpServletResponse httpResponse,
      FilterChain chain,
      Authentication authResult)
      throws AuthenticationException {

    try {
      CustomPrincipal principal = (CustomPrincipal) authResult.getPrincipal();
      Long userId = principal.id();

      TokenPair tokenPair = authenticationService.createAuthenticationToken(userId);

      authenticationService.saveRefreshToken(
          tokenPair.getTokenId(), userId, tokenPair.getRefreshTokenCredential());

      LoginResponse response = createResponse(tokenPair, principal);
      authResponseSender.sendSuccessResponse(httpResponse, response);

      log.info("로그인 성공 user: id-{}", userId);
    } catch (Exception e) {
      log.error("[Exception] 인증 중 오류 발생: ", e);
      throw new AuthenticationException("로그인에 실패했습니다.") {};
    }
  }

  @Override
  protected void unsuccessfulAuthentication(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
    log.info("[AuthenticationException] 인증 실패: {}", failed.getMessage());
    authResponseSender.sendErrorResponse(
        request, response, HttpServletResponse.SC_UNAUTHORIZED, failed.getMessage(), null);
  }

  private LoginResponse createResponse(TokenPair tokenPair, CustomPrincipal principal) {
    return LoginResponse.create(
        true,
        tokenPair.getAccessTokenCredential().tokenValue(),
        tokenPair.getRefreshTokenCredential().tokenValue(),
        principal.id(),
        principal.nickname(),
        principal.role());
  }

  private List<GrantedAuthority> extractAuthorities(String role) {
    return List.of(new SimpleGrantedAuthority(role));
  }
}
