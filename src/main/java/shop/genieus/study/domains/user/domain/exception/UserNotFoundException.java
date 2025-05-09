package shop.genieus.study.domains.user.domain.exception;

import lombok.Getter;
import shop.genieus.study.commons.exception.Domain;
import shop.genieus.study.commons.exception.NotFoundException;

@Getter
public class UserNotFoundException extends NotFoundException {
  private static final String USER_NOT_FOUND_MESSAGE = "유저를 찾을 수 없습니다.";

  private UserNotFoundException(String message) {
    super(message, Domain.USER);
  }

  public static UserNotFoundException create() {
    return new UserNotFoundException(USER_NOT_FOUND_MESSAGE);
  }
}
