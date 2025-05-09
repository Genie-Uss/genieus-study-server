package shop.genieus.study.domains.user.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.genieus.study.domains.user.domain.exception.UserValidationException;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email {
  private static final int MAX_LENGTH = 20;
  private static final String EMAIL_REGEX =
      "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
  private static final String Email_FORMAT = "영문자, 숫자, 특수문자(_+&*-)";

  @Column(name = "email", length = MAX_LENGTH, unique = true, nullable = false)
  private String value;

  private Email(String value) {
    validate(value);
    this.value = value;
  }

  public static Email of(String value) {
    return new Email(value);
  }

  private void validate(String value) {
    if (value == null || value.isBlank()) {
      throw UserValidationException.requiredEmail();
    }

    if (!value.matches(EMAIL_REGEX)) {
      throw UserValidationException.invalidEmailFormat(Email_FORMAT);
    }

    if (value.length() > 20) {
      throw UserValidationException.exceedsEmailMaxLength(MAX_LENGTH);
    }
  }
}
