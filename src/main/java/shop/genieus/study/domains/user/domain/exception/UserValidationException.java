package shop.genieus.study.domains.user.domain.exception;

import lombok.Getter;
import shop.genieus.study.commons.exception.Domain;
import shop.genieus.study.commons.exception.ValidationException;

@Getter
public class UserValidationException extends ValidationException {

  private UserValidationException(String message) {
    super(message, Domain.USER);
  }

  public static UserValidationException noEmailOrPassword() {
    return new UserValidationException("아이디 또는 비밀번호가 잘못되었습니다. 다시 입력해 주세요");
  }

  public static UserValidationException duplicateEmail(String email) {
    return new UserValidationException(email + "은(는) 이미 가입된 이메일 입니다");
  }

  public static UserValidationException noPasswordConfirm() {
    return new UserValidationException("비밀번호 확인이 일치하지 않습니다");
  }

  public static UserValidationException requiredEmail() {
    return new UserValidationException("이메일은 필수 입력값입니다");
  }

  public static UserValidationException invalidEmailFormat(String format) {
    return new UserValidationException("이메일은" + format + "로 구성된 아이디와 도메인으로 구성되어야 합니다.");
  }

  public static UserValidationException exceedsEmailMaxLength(int maxLength) {
    return new UserValidationException("이메일은 " + maxLength + "자를 초과할 수 없습니다");
  }

  public static UserValidationException requiredNickname() {
    return new UserValidationException("이름은 필수 입력값입니다");
  }

  public static UserValidationException exceedsNicknameMaxLength(int maxLength) {
    return new UserValidationException("이름은 " + maxLength + "자 이내여야 합니다");
  }

  public static UserValidationException requiredPassword() {
    return new UserValidationException("비밀번호는 필수 입력값입니다");
  }

  public static UserValidationException invalidPasswordPattern(int min, int max) {
    return new UserValidationException(
        "비밀번호는 %d~%d자 사이의 대문자, 소문자, 숫자, 특수문자를 모두 포함해야 합니다".formatted(min, max));
  }

  public static UserValidationException invalidUserRole(String userRole) {
    return new UserValidationException("잘못된 사용자 역할입니다: " + userRole);
  }

  public static UserValidationException accountPending() {
    return new UserValidationException("계정 승인 대기 중입니다. 관리자 승인 후 로그인이 가능합니다.");
  }

  public static UserValidationException accountRejected() {
    return new UserValidationException("계정 승인이 거부되었습니다. 관리자에게 문의하세요.");
  }

  public static UserValidationException accountInactive() {
    return new UserValidationException("비활성화된 계정입니다. 관리자에게 문의하세요.");
  }

  public static UserValidationException accountLocked() {
    return new UserValidationException("계정이 잠겨 있습니다. 관리자에게 문의하세요.");
  }

  public static UserValidationException accountNotApproved() {
    return new UserValidationException("로그인할 수 없는 계정 상태입니다. 관리자에게 문의하세요.");
  }
}
