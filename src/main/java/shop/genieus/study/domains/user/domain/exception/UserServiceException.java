package shop.genieus.study.domains.user.domain.exception;

import lombok.Getter;
import shop.genieus.study.commons.exception.ApiBusinessException;
import shop.genieus.study.commons.exception.Domain;

@Getter
public class UserServiceException extends ApiBusinessException {
  public UserServiceException(String message) {
    super(message, Domain.USER);
  }
}
