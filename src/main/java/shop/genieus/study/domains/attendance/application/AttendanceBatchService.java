package shop.genieus.study.domains.attendance.application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.genieus.study.commons.provider.DateTimeProvider;
import shop.genieus.study.domains.attendance.application.repository.AttendanceRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendanceBatchService {

  private final AttendanceRepository attendanceRepository;
  private final DateTimeProvider dateTimeProvider;

  @Transactional
  @Scheduled(cron = "${scheduler.attendance.system-check-out-cron}")
  public void autoCheckOutAttendances() {
    LocalDate yesterday = dateTimeProvider.getCurrentDate().minusDays(1);
    LocalDateTime checkoutTime = yesterday.atTime(23, 59, 59);

    int updatedCount =
        attendanceRepository.batchCheckOutUncheckedAttendances(yesterday, checkoutTime);

    log.info("자동 체크아웃 배치 작업 완료: 날짜={}, 처리된 출석 수={}", yesterday, updatedCount);
  }
}
