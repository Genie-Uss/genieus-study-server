package shop.genieus.study.domains.auth.domain.exception;

import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationException extends AuthenticationException {
  private static final String TOKEN_UNAUTHORIZED = "토큰이 없거나 너무 짧습니다";
  private static final String ACCESS_TOKEN_VALID = "현재 액세스 토큰은 유효합니다. 새 토큰이 필요하지 않습니다.";
  private static final String NEED_AUTHENTICATION = "인증이 필요한 엔드포인트입니다.";

  private CustomAuthenticationException(String message) {
    super(message);
  }

  public static CustomAuthenticationException unauthorized() {
    return new CustomAuthenticationException(TOKEN_UNAUTHORIZED);
  }

  public static CustomAuthenticationException tokenValid(String message) {
    return new CustomAuthenticationException(ACCESS_TOKEN_VALID);
  }

  public static CustomAuthenticationException needAuthentication() {
    return new CustomAuthenticationException(NEED_AUTHENTICATION);
  }

  public static CustomAuthenticationException create(String message) {
    return new CustomAuthenticationException(message);
  }
}
