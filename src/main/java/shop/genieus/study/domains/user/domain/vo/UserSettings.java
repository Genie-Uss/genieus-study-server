package shop.genieus.study.domains.user.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import shop.genieus.study.domains.user.domain.exception.UserValidationException;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSettings {
  private static final int MIN_CORE_TIME = 60;
  private static final int MAX_CORE_TIME = 720;
  private static final LocalTime MIN_CHECK_IN_TIME = LocalTime.of(6, 0);
  private static final LocalTime MAX_CHECK_IN_TIME = LocalTime.of(12, 0);

  @Comment("희망 출석 시각")
  @Column(name = "desired_check_in_time", nullable = false)
  private LocalTime desiredCheckInTime;

  @Comment("희망 코어 시간 (분)")
  @Column(name = "desired_core_time", nullable = false)
  private int desiredCoreTime;

  private UserSettings(LocalTime desiredCheckInTime, int desiredCoreTime) {
    validateCheckInTime(desiredCheckInTime);
    validateCoreTime(desiredCoreTime);

    this.desiredCheckInTime = desiredCheckInTime;
    this.desiredCoreTime = desiredCoreTime;
  }

  public static UserSettings of(LocalTime desiredCheckInTime, int desiredCoreTime) {
    return new UserSettings(desiredCheckInTime, desiredCoreTime);
  }

  public static UserSettings defaultSettings() {
    return new UserSettings(LocalTime.of(9, 0), 240);
  }

  private static void validateCheckInTime(LocalTime checkInTime) {
    if (checkInTime == null) {
      throw UserValidationException.requiredCheckInTime();
    }
    if (checkInTime.isBefore(MIN_CHECK_IN_TIME) || checkInTime.isAfter(MAX_CHECK_IN_TIME)) {
      throw UserValidationException.invalidCheckInTimeRange();
    }
  }

  private static void validateCoreTime(int coreTime) {
    if (coreTime < MIN_CORE_TIME || coreTime > MAX_CORE_TIME) {
      throw UserValidationException.invalidCoreTimeRange(MIN_CORE_TIME, MAX_CORE_TIME);
    }
  }

  public boolean isSameAs(LocalTime checkInTime, int coreTime) {
    return this.desiredCheckInTime.equals(checkInTime) && this.desiredCoreTime == coreTime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UserSettings that)) return false;
    return desiredCoreTime == that.desiredCoreTime
        && Objects.equals(desiredCheckInTime, that.desiredCheckInTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(desiredCheckInTime, desiredCoreTime);
  }

  @Override
  public String toString() {
    return "UserSettings(checkInTime="
        + desiredCheckInTime
        + ", coreTime="
        + desiredCoreTime
        + "분)";
  }
}
