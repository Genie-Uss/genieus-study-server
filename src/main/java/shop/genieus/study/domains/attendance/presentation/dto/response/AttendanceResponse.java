package shop.genieus.study.domains.attendance.presentation.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import shop.genieus.study.domains.attendance.domain.entity.Attendance;
import shop.genieus.study.domains.attendance.domain.vo.AttendanceTime;

public record AttendanceResponse(
    Long id,
    Long userId,
    LocalDate date,
    boolean isCheckedIn,
    boolean isCheckedOut,
    LocalDateTime checkInTime,
    LocalDateTime checkOutTime,
    int studyDuration,
    boolean isOwner) {

  public static AttendanceResponse from(Attendance attendance, Long userId, Long targetUserId) {
    AttendanceTime attendanceTime = attendance.getAttendanceTime();
    return new AttendanceResponse(
        attendance.getId(),
        attendance.getUserId(),
        attendanceTime.getDate(),
        attendance.isCheckedIn(),
        attendance.isCheckedOut(),
        attendanceTime.getCheckInTime(),
        attendanceTime.getCheckOutTime(),
        attendance.getStudyResult().getStudyMinutes(),
        userId.equals(targetUserId));
  }
}
