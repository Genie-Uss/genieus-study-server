package shop.genieus.study.domains.user.application.exception;

import lombok.Getter;
import shop.genieus.study.commons.exception.Domain;
import shop.genieus.study.commons.exception.NotFoundException;

@Getter
public class UserNotFoundException extends NotFoundException {
  private UserNotFoundException(String message) {
    super(message, Domain.USER);
  }

  public static UserNotFoundException create() {
    return new UserNotFoundException("사용자를 찾을 수 없습니다.");
  }
}
