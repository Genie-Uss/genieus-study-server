package shop.genieus.study.domains.user.domain.exception;

import lombok.Getter;
import shop.genieus.study.commons.exception.Domain;
import shop.genieus.study.commons.exception.ValidationException;

@Getter
public class UserSameSettingException extends ValidationException {
  private UserSameSettingException(String message) {
    super(message, Domain.USER);
  }

  public UserSameSettingException() {
    this("기존 설정과 동일합니다.");
  }
}
