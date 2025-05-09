package shop.genieus.study.domains.auth.domain.exception;

import lombok.Getter;
import shop.genieus.study.commons.exception.ApiBusinessException;
import shop.genieus.study.commons.exception.Domain;

@Getter
public class AuthServiceException extends ApiBusinessException {
  public AuthServiceException(String message) {
    super(message, Domain.AUTH);
  }
}
