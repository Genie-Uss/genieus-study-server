package shop.genieus.study.domains.attendance.application.repository;

import java.time.LocalDate;
import shop.genieus.study.domains.attendance.domain.entity.Attendance;

public interface AttendanceRepository {
  boolean existsByUserIdAndAttendanceTimeDate(Long userId, LocalDate checkInDate);

  Attendance save(Attendance attendance);
}
