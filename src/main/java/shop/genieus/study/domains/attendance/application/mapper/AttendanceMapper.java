package shop.genieus.study.domains.attendance.application.mapper;

import org.springframework.stereotype.Component;
import shop.genieus.study.commons.provider.model.AttendanceInfo;
import shop.genieus.study.domains.attendance.domain.entity.Attendance;
import shop.genieus.study.domains.attendance.domain.vo.AttendanceTime;
import shop.genieus.study.domains.attendance.domain.vo.StudyResult;

@Component
public class AttendanceMapper {
  public AttendanceInfo from(Attendance attendance) {
    AttendanceTime attendanceTime = attendance.getAttendanceTime();
    StudyResult studyResult = attendance.getStudyResult();

    return new AttendanceInfo(
        attendance.getId(),
        attendance.getUserId(),
        attendanceTime.getDate(),
        attendance.getDesiredCoreTime(),
        true,
        attendanceTime.getCheckInTime(),
        attendanceTime.isCheckedIn(),
        attendanceTime.getCheckOutTime(),
        attendanceTime.isCheckedOut(),
        attendanceTime.isLate(),
        attendanceTime.getDesiredCheckInTime(),
        studyResult.getStudyMinutes(),
        studyResult.getAchievementRate());
  }
}
