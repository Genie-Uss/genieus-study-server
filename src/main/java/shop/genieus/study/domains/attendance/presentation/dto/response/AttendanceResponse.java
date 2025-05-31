package shop.genieus.study.domains.attendance.presentation.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import shop.genieus.study.domains.attendance.application.dto.result.AttendanceResult;

public record AttendanceResponse(
    Long id,
    Long userId,
    LocalDate date,
    boolean isCheckedIn,
    boolean isCheckedOut,
    LocalDateTime checkInTime,
    LocalDateTime checkOutTime,
    int desiredCoreTime,
    int studyDuration,
    boolean isOwner,
    boolean hasAttendanceRecord) {

  public static AttendanceResponse from(AttendanceResult result) {
    return new AttendanceResponse(
        result.id(),
        result.userId(),
        result.date(),
        result.isCheckedIn(),
        result.isCheckedOut(),
        result.checkInTime(),
        result.checkOutTime(),
        result.desiredCoreTime(),
        result.studyMinutes(),
        result.isOwner(),
        result.hasAttendanceRecord());
  }
}
