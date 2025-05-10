package shop.genieus.study.domains.attendance.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import shop.genieus.study.domains.attendance.domain.exception.AttendanceValidationException;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttendanceTime {
  @Comment("출석 일자")
  @Column(name = "date")
  private LocalDate date;

  @Comment("출석 일시")
  @Column(name = "check_in_time")
  private LocalDateTime checkInTime;

  @Comment("퇴실 일시")
  @Column(name = "check_out_time")
  private LocalDateTime checkOutTime;

  @Comment("사용자가 희망한 출석 기준")
  @Column(name = "desired_check_in_time")
  private LocalTime desiredCheckInTime;

  @Comment("지각 여부 (희망 체크인 시간 이후 체크인 시 true)")
  @Column(name = "is_late")
  private boolean isLate;

  private AttendanceTime(
      LocalDate date,
      LocalDateTime checkInTime,
      LocalDateTime checkOutTime,
      LocalTime desiredCheckInTime,
      boolean isLate) {
    this.date = date;
    this.checkInTime = checkInTime;
    this.checkOutTime = checkOutTime;
    this.desiredCheckInTime = desiredCheckInTime;
    this.isLate = isLate;
  }

  public static AttendanceTime of(
      LocalDateTime checkInTime,
      LocalDateTime checkOutTime,
      LocalTime desiredCheckInTime,
      LocalDate currentDate,
      LocalDateTime currentDateTime) {
    validateCheckInTime(checkInTime, currentDate, currentDateTime);
    LocalDate date = checkInTime.toLocalDate();

    boolean isLate = false;
    if (desiredCheckInTime != null && checkInTime != null) {
      isLate = checkInTime.toLocalTime().isAfter(desiredCheckInTime);
    }

    return new AttendanceTime(date, checkInTime, checkOutTime, desiredCheckInTime, isLate);
  }

  private static void validateCheckInTime(
      LocalDateTime checkInTime, LocalDate currentDate, LocalDateTime currentDateTime) {
    if (checkInTime == null) {
      throw AttendanceValidationException.requiredCheckInTime();
    }
    if (!checkInTime.toLocalDate().equals(currentDate)) {
      throw AttendanceValidationException.notTodayCheckIn();
    }

    if (checkInTime.isAfter(currentDateTime)) {
      throw AttendanceValidationException.futureTimeCheckIn();
    }
  }

  public AttendanceTime updateCheckOutTime(
      LocalDateTime checkOutTime, LocalDate currentDate, LocalDateTime currentDateTime) {
    validateCheckOutTime(checkOutTime, currentDate, currentDateTime);

    return new AttendanceTime(
        this.date, this.checkInTime, checkOutTime, this.desiredCheckInTime, this.isLate);
  }

  private void validateCheckOutTime(
      LocalDateTime checkOutTime, LocalDate currentDate, LocalDateTime currentDateTime) {
    if (checkOutTime == null) {
      throw AttendanceValidationException.requiredCheckOutTime();
    }

    if (checkOutTime.isBefore(this.checkInTime)) {
      throw AttendanceValidationException.checkOutBeforeCheckIn();
    }

    if (!checkOutTime.toLocalDate().equals(currentDate)) {
      throw AttendanceValidationException.notTodayCheckOut();
    }

    if (checkOutTime.isAfter(currentDateTime)) {
      throw AttendanceValidationException.futureTimeCheckOut();
    }
  }

  public int calculateStudyMinutes() {
    if (!isCheckedOut()) {
      return 0;
    }

    return (int) java.time.temporal.ChronoUnit.MINUTES.between(checkInTime, checkOutTime);
  }

  public boolean isCheckedOut() {
    return checkOutTime != null;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AttendanceTime that = (AttendanceTime) o;
    return isLate == that.isLate
        && Objects.equals(date, that.date)
        && Objects.equals(checkInTime, that.checkInTime)
        && Objects.equals(checkOutTime, that.checkOutTime)
        && Objects.equals(desiredCheckInTime, that.desiredCheckInTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(date, checkInTime, checkOutTime, desiredCheckInTime, isLate);
  }
}
