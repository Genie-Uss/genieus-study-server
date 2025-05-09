package shop.genieus.study.domains.user.domain.exception;

import lombok.Getter;
import shop.genieus.study.commons.exception.Domain;
import shop.genieus.study.commons.exception.ValidationException;

@Getter
public class UserValidationException extends ValidationException {
  private static final String DUPLICATED_EMAIL = "이미 가입된 이메일 입니다";
  private static final String DIFFERENT_PASSWORD_CONFIRM = "비밀번호 확인이 일치하지 않습니다";
  private static final String REQUIRED_EMAIL = "이메일은 필수 입력값입니다";
  private static final String INVALID_EMAIL_FORMAT =
      "이메일은 %s로 구성된 아이디와 도메인으로 구성되어야 합니다.";
  private static final String EXCEEDS_EMAIL_MAX_LENGTH = "이메일은 %d자를 초과할 수 없습니다";

  private static final String REQUIRED_NICKNAME = "이름은 필수 입력값입니다";
  private static final String EXCEEDS_NICKNAME_MAX_LENGTH = "이름은 %d자 이내여야 합니다";

  private static final String REQUIRED_PASSWORD = "비밀번호는 필수 입력값입니다";
  private static final String INVALID_PASSWORD_PATTERN =
      "비밀번호는 %d~%d자 사이의 대문자, 소문자, 숫자, 특수문자를 모두 포함해야 합니다";
  private static final String NO_EMAIL_OR_PASSWORD = "아이디 또는 비밀번호가 잘못되었습니다. 다시 입력해 주세요";

  private UserValidationException(String message) {
    super(message, Domain.USER);
  }

  public static UserValidationException noEmailOrPassword() {
    return new UserValidationException(NO_EMAIL_OR_PASSWORD);
  }

  public static UserValidationException duplicateEmail(String email) {
    return new UserValidationException(email + "은(는) " + DUPLICATED_EMAIL);
  }

  public static UserValidationException noPasswordConfirm() {
    return new UserValidationException(DIFFERENT_PASSWORD_CONFIRM);
  }

  public static UserValidationException requiredEmail() {
    return new UserValidationException(REQUIRED_EMAIL);
  }

  public static UserValidationException invalidEmailFormat(String format) {
    return new UserValidationException(INVALID_EMAIL_FORMAT.formatted(format));
  }

  public static UserValidationException exceedsEmailMaxLength(int maxLength) {
    return new UserValidationException(EXCEEDS_EMAIL_MAX_LENGTH.formatted(maxLength));
  }

  public static UserValidationException requiredNickname() {
    return new UserValidationException(REQUIRED_NICKNAME);
  }

  public static UserValidationException exceedsNicknameMaxLength(int maxLength) {
    return new UserValidationException(EXCEEDS_NICKNAME_MAX_LENGTH.formatted(maxLength));
  }

  public static UserValidationException requiredPassword() {
    return new UserValidationException(REQUIRED_PASSWORD);
  }

  public static UserValidationException invalidPasswordPattern(int min, int max) {
    return new UserValidationException(INVALID_PASSWORD_PATTERN.formatted(min, max));
  }
}
