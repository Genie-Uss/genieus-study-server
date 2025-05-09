package shop.genieus.study.domains.auth.domain.exception;

import org.springframework.security.access.AccessDeniedException;

public class CustomAccessDeniedException extends AccessDeniedException {
  private static final String TOKEN_MALFORM = "잘못된 토큰입니다";
  private static final String ACCESS_TOKEN_EXPIRED = "액세스 토큰이 만료되었습니다";
  private static final String ACCESS_TOKEN_BLACKLISTED = "이 액세스 토큰은 사용이 불가능합니다.";
  private static final String REFRESH_TOKEN_EXPIRED = "리프레시 토큰이 만료되었습니다";
  private static final String NO_AUTHENTICATION = "해당 리소스에 대한 권한이 없습니다.";

  private CustomAccessDeniedException(String message) {
    super(message);
  }

  public static CustomAccessDeniedException malForm() {
    return new CustomAccessDeniedException(TOKEN_MALFORM);
  }

  public static CustomAccessDeniedException accessTokenExpired() {
    return new CustomAccessDeniedException(ACCESS_TOKEN_EXPIRED);
  }

  public static CustomAccessDeniedException accessTokenBlacklisted() {
    return new CustomAccessDeniedException(ACCESS_TOKEN_BLACKLISTED);
  }

  public static CustomAccessDeniedException refreshTokenExpired() {
    return new CustomAccessDeniedException(REFRESH_TOKEN_EXPIRED);
  }

  public static CustomAccessDeniedException noAuthentication() {
    return new CustomAccessDeniedException(NO_AUTHENTICATION);
  }
}
