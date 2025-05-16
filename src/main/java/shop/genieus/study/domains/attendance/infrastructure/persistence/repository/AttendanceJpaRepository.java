package shop.genieus.study.domains.attendance.infrastructure.persistence.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.genieus.study.domains.attendance.domain.entity.Attendance;

public interface AttendanceJpaRepository extends JpaRepository<Attendance, Long> {
  boolean existsByUserIdAndAttendanceTimeDate(Long userId, LocalDate date);

  Optional<Attendance> findByUserIdAndAttendanceTimeDate(Long userId, LocalDate date);

  @Modifying
  @Query(
      value =
          "UPDATE g_attendances a "
              + "SET a.check_out_time = :checkOutTime, "
              + "a.study_minutes = TIMESTAMPDIFF(MINUTE, a.check_in_time, :checkOutTime), "
              + "a.achievement_rate = LEAST(100.0, (TIMESTAMPDIFF(MINUTE, a.check_in_time, :checkOutTime) / a.desired_core_time) * 100.0) "
              + "WHERE a.date = :date AND a.check_out_time IS NULL",
      nativeQuery = true)
  int batchCheckOutUncheckedAttendances(
      @Param("date") LocalDate date, @Param("checkOutTime") LocalDateTime checkOutTime);
}
