package shop.genieus.study.domains.auth.domain.exception;

import shop.genieus.study.commons.exception.Domain;
import shop.genieus.study.commons.exception.ValidationException;

public class TokenExpiredException extends ValidationException {
  public TokenExpiredException(String message) {
    super(message, Domain.AUTH);
  }

  public static TokenExpiredException create() {
    return new TokenExpiredException("만료된 JWT 토큰입니다.");
  }
}
