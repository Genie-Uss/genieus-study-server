package shop.genieus.study.domains.attendance.presentation.dto.response;

import java.time.LocalDateTime;
import shop.genieus.study.domains.attendance.domain.entity.Attendance;
import shop.genieus.study.domains.attendance.domain.vo.AttendanceTime;
import shop.genieus.study.domains.attendance.domain.vo.StudyResult;

public record CheckOutResponse(
    Long id,
    Long userId,
    LocalDateTime checkInTime,
    LocalDateTime checkOutTime,
    boolean isLate,
    Integer studyMinutes,
    Double achievementRate) {

  public static CheckOutResponse from(Attendance attendance) {
    AttendanceTime attendanceTime = attendance.getAttendanceTime();
    StudyResult studyResult = attendance.getStudyResult();

    return new CheckOutResponse(
        attendance.getId(),
        attendance.getUserId(),
        attendanceTime.getCheckInTime(),
        attendanceTime.getCheckOutTime(),
        attendanceTime.isLate(),
        studyResult.getStudyMinutes(),
        studyResult.getAchievementRate());
  }
}
