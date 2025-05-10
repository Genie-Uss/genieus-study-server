package shop.genieus.study.domains.attendance.infrastructure.persistence.repository;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.genieus.study.domains.attendance.domain.entity.Attendance;

public interface AttendanceJpaRepository extends JpaRepository<Attendance, Long> {
  boolean existsByUserIdAndAttendanceTimeDate(Long userId, LocalDate date);
}
