package shop.genieus.study.domains.attendance.presentation.dto.response;

import java.time.LocalDateTime;
import shop.genieus.study.domains.attendance.domain.entity.Attendance;
import shop.genieus.study.domains.attendance.domain.vo.AttendanceTime;

public record CheckInResponse(Long id, Long userId, LocalDateTime checkInTime, Boolean isLate) {
  public static CheckInResponse from(Attendance attendance) {
    AttendanceTime attendanceTime = attendance.getAttendanceTime();
    return new CheckInResponse(
        attendance.getId(),
        attendance.getUserId(),
        attendanceTime.getCheckInTime(),
        attendanceTime.isLate());
  }
}
