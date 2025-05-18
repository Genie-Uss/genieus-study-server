package shop.genieus.study.domains.auth.domain.exception;

import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationException extends AuthenticationException {
  private CustomAuthenticationException(String message) {
    super(message);
  }

  public static CustomAuthenticationException create(String message) {
    return new CustomAuthenticationException(message);
  }
}
