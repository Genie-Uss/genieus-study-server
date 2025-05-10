package shop.genieus.study.domains.attendance.domain.exception;

import lombok.Getter;
import shop.genieus.study.commons.exception.Domain;
import shop.genieus.study.commons.exception.ValidationException;

@Getter
public class AttendanceValidationException extends ValidationException {

  private AttendanceValidationException(String message) {
    super(message, Domain.ATTENDANCE);
  }

  public static AttendanceValidationException requiredCheckInTime() {
    return new AttendanceValidationException("출석 시간은 필수입니다.");
  }

  public static AttendanceValidationException invalidActualStudyMinutes(int minutes) {
    return new AttendanceValidationException("실제 공부 시간은" + minutes + "분 이상이어야 합니다.");
  }

  public static AttendanceValidationException invalidTargetStudyMinutes(int minutes) {
    return new AttendanceValidationException("목표 공부 시간은 " + minutes + "분 이상이어야 합니다.");
  }

  public static AttendanceValidationException notTodayCheckIn() {
    return new AttendanceValidationException("오늘 날짜에만 출석이 가능합니다.");
  }

  public static AttendanceValidationException futureTimeCheckIn() {
    return new AttendanceValidationException("미래 시간으로 출석할 수 없습니다.");
  }

  public static AttendanceValidationException alreadyCheckedOut() {
    return new AttendanceValidationException("이미 퇴실 처리되었습니다.");
  }

  public static AttendanceValidationException requiredCheckOutTime() {
    return new AttendanceValidationException("퇴실 시간은 필수입니다.");
  }

  public static AttendanceValidationException checkOutBeforeCheckIn() {
    return new AttendanceValidationException("퇴실 시간은 출석 시간 이후여야 합니다.");
  }

  public static AttendanceValidationException notTodayCheckOut() {
    return new AttendanceValidationException("오늘 날짜에만 퇴실이 가능합니다.");
  }

  public static AttendanceValidationException futureTimeCheckOut() {
    return new AttendanceValidationException("미래 시간으로 퇴실할 수 없습니다.");
  }
}
