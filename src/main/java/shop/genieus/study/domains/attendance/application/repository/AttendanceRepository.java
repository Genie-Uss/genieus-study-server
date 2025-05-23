package shop.genieus.study.domains.attendance.application.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import shop.genieus.study.domains.attendance.domain.entity.Attendance;

public interface AttendanceRepository {
  boolean existsByUserIdAndAttendanceTimeDate(Long userId, LocalDate checkInDate);

  Attendance save(Attendance attendance);

  Attendance findByUserIdAndAttendanceTimeDate(Long aLong, LocalDate today);

  int batchCheckOutUncheckedAttendances(LocalDate date, LocalDateTime checkOutTime);
}
