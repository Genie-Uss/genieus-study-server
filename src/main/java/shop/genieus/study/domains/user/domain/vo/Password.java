package shop.genieus.study.domains.user.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.genieus.study.domains.user.application.PasswordEncryptionService;
import shop.genieus.study.domains.user.domain.PasswordService;
import shop.genieus.study.domains.user.domain.exception.UserValidationException;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {
  private static final int MIN_LENGTH = 8;
  private static final int MAX_LENGTH = 20;
  private static final String PASSWORD_PATTERN =
      "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{}|;:,.<>?])(.{"
          + MIN_LENGTH
          + ","
          + MAX_LENGTH
          + "})$";

  @Column(name = "password", length = 255, nullable = false)
  private String value;

  private Password(String value) {
    this.value = value;
  }

  public static Password of(String plainPassword, PasswordService passwordEncryptionService) {
    validatePassword(plainPassword);
    return new Password(passwordEncryptionService.encode(plainPassword));
  }

  private static void validatePassword(String plainPassword) {
    if (plainPassword == null || plainPassword.isBlank()) {
      throw UserValidationException.requiredPassword();
    }

    if (!plainPassword.matches(PASSWORD_PATTERN)) {
      throw UserValidationException.invalidPasswordPattern(MIN_LENGTH, MAX_LENGTH);
    }
  }

  public boolean matches(
      String plainPassword, PasswordEncryptionService passwordEncryptionService) {
    return passwordEncryptionService.matches(plainPassword, this.value);
  }
}
