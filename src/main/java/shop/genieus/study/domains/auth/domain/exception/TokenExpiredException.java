package shop.genieus.study.domains.auth.domain.exception;

import jakarta.security.auth.message.AuthException;
import shop.genieus.study.commons.exception.Domain;
import shop.genieus.study.commons.exception.ValidationException;

public class TokenExpiredException extends ValidationException {
  private static final String EXPIRED_TOKEN = "만료된 JWT 토큰입니다.";

  public TokenExpiredException(String message) {
    super(message, Domain.AUTH);
  }

  public static TokenExpiredException create() {
    return new TokenExpiredException(EXPIRED_TOKEN);
  }
}
