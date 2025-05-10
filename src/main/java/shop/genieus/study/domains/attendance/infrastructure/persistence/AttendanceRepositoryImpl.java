package shop.genieus.study.domains.attendance.infrastructure.persistence;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.genieus.study.domains.attendance.application.exception.AttendanceNotFoundException;
import shop.genieus.study.domains.attendance.application.repository.AttendanceRepository;
import shop.genieus.study.domains.attendance.domain.entity.Attendance;
import shop.genieus.study.domains.attendance.infrastructure.persistence.repository.AttendanceJpaRepository;

@Repository
@RequiredArgsConstructor
public class AttendanceRepositoryImpl implements AttendanceRepository {
  private final AttendanceJpaRepository jpaRepository;

  @Override
  public boolean existsByUserIdAndAttendanceTimeDate(Long userId, LocalDate checkInDate) {
    return jpaRepository.existsByUserIdAndAttendanceTimeDate(userId, checkInDate);
  }

  @Override
  public Attendance save(Attendance attendance) {
    return jpaRepository.save(attendance);
  }
}
