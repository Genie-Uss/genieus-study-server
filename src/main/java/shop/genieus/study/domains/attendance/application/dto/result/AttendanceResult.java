package shop.genieus.study.domains.attendance.application.dto.result;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import shop.genieus.study.domains.attendance.domain.entity.Attendance;
import shop.genieus.study.domains.attendance.domain.vo.AttendanceTime;
import shop.genieus.study.domains.attendance.domain.vo.StudyResult;

public record AttendanceResult(
    Long id,
    Long userId,
    int desiredCoreTime,
    LocalDate date,
    boolean isCheckedIn,
    boolean isCheckedOut,
    LocalDateTime checkInTime,
    LocalDateTime checkOutTime,
    LocalTime desiredCheckInTime,
    boolean isLate,
    boolean isOwner,
    int studyMinutes,
    double achievementRate,
    boolean hasAttendanceRecord) {

  public static AttendanceResult from(Attendance attendance, Long requestUserId) {
    AttendanceTime attendanceTime = attendance.getAttendanceTime();
    StudyResult studyResult = attendance.getStudyResult();
    return new AttendanceResult(
        attendance.getId(),
        attendance.getUserId(),
        attendance.getDesiredCoreTime(),
        attendanceTime.getDate(),
        attendance.isCheckedIn(),
        attendance.isCheckedOut(),
        attendanceTime.getCheckInTime(),
        attendanceTime.getCheckOutTime(),
        attendanceTime.getDesiredCheckInTime(),
        attendanceTime.isLate(),
        attendance.isOwnedBy(requestUserId),
        studyResult.getStudyMinutes(),
        studyResult.getAchievementRate(),
        true);
  }

  public static AttendanceResult notFound(
      Long targetUserId,
      Long requestUserId,
      int coreTime,
      LocalDate targetDate,
      LocalTime desiredCheckInTime) {
    return new AttendanceResult(
        null,
        targetUserId,
        coreTime,
        targetDate,
        false,
        false,
        null,
        null,
        desiredCheckInTime,
        false,
        Objects.equals(targetUserId, requestUserId),
        0,
        0,
        false);
  }
}
