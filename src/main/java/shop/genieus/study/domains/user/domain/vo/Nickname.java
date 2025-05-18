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
public class Nickname {
  private static final int MAX_LENGTH = 5;

  @Column(name = "nickname", length = MAX_LENGTH, nullable = false, unique = true)
  private String value;

  private Nickname(String value) {
    validateNickname(value);
    this.value = value;
  }

  public static Nickname of(String value) {
    return new Nickname(value);
  }

  private void validateNickname(String name) {
    if (name == null || name.isBlank()) {
      throw UserValidationException.requiredNickname();
    }

    if (name.length() > MAX_LENGTH) {
      throw UserValidationException.exceedsNicknameMaxLength(MAX_LENGTH);
    }
  }
}
