package shop.genieus.study.domains.auth.domain.exception;

import lombok.Getter;
import shop.genieus.study.commons.exception.Domain;
import shop.genieus.study.commons.exception.ValidationException;

@Getter
public class AuthValidationException extends ValidationException {
  private static final String DUPLICATED_EMAIL = "이미 가입된 이메일 입니다";
  private static final String DIFFERENT_PASSWORD_CONFIRM = "비밀번호 확인이 일치하지 않습니다";
  private static final String NOT_EXIST_ROLE = "없는 권한입니다";

  private AuthValidationException(String message) {
    super(message, Domain.AUTH);
  }

  public static AuthValidationException duplicateEmail(String email) {
    return new AuthValidationException(email + "은(는) " + DUPLICATED_EMAIL);
  }

  public static AuthValidationException notExistRole(String role) {
    return new AuthValidationException(role + "은(는) " + NOT_EXIST_ROLE);
  }

  public static AuthValidationException noPasswordConfirm() {
    return new AuthValidationException(DIFFERENT_PASSWORD_CONFIRM);
  }
}
