package shop.genieus.study.domains.notification.application;

import java.time.LocalDate;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.genieus.study.commons.provider.AttendanceProvider;
import shop.genieus.study.commons.provider.DateTimeProvider;
import shop.genieus.study.commons.provider.StampHistoryProvider;
import shop.genieus.study.commons.provider.UserProvider;
import shop.genieus.study.commons.provider.dto.AttendanceInfo;
import shop.genieus.study.commons.provider.dto.StampHistoryInfo;
import shop.genieus.study.commons.provider.dto.UserInfo;
import shop.genieus.study.domains.notification.application.assembler.DailyStatisticsAssembler;
import shop.genieus.study.domains.notification.application.event.DailyStatisticsEvent;
import shop.genieus.study.domains.notification.domain.vo.UserStatistics;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DailyStatisticsService {

  private final AttendanceProvider attendanceProvider;
  private final StampHistoryProvider stampHistoryProvider;
  private final UserProvider userProvider;
  private final DailyStatisticsAssembler assembler;
  private final DateTimeProvider dateTimeProvider;
  private final ApplicationEventPublisher eventPublisher;

  @Scheduled(cron = "${scheduler.notification.daily-statistics-cron}")
  public void sendDailyStatistics() {
    LocalDate yesterday = dateTimeProvider.getYesterday();
    log.info("일일 통계 수집 시작: {}", yesterday);

    try {
      List<UserInfo> activeUsers = userProvider.getAllActiveUsers();
      if (activeUsers.isEmpty()) {
        log.info("활성 사용자가 없어 통계 생성을 건너뜁니다.");
        return;
      }

      Map<Long, String> userNicknameMap = new HashMap<>();
      List<Long> userIds = new ArrayList<>();
      for (UserInfo userInfo : activeUsers) {
        userNicknameMap.put(userInfo.id(), userInfo.nickname());
        userIds.add(userInfo.id());
      }

      log.debug("통계 대상 사용자: {} 명, user Id: {}", userIds.size(), userIds);

      Map<Long, AttendanceInfo> attendanceInfos =
          attendanceProvider.getAttendances(userIds, yesterday);
      Map<Long, StampHistoryInfo> stampHistoryInfos =
          stampHistoryProvider.getStampHistories(userIds, yesterday);

      List<UserStatistics> userStatistics =
          assembler.assembleUserStatistics(
              userIds, userNicknameMap, attendanceInfos, stampHistoryInfos);

      DailyStatisticsEvent event = new DailyStatisticsEvent(yesterday, userStatistics);
      eventPublisher.publishEvent(event);

      log.info("일일 통계 알림 발송 완료: {} ({} users)", yesterday, userStatistics.size());
    } catch (Exception e) {
      log.warn("일일 통계 처리 중 오류 발생: {}", yesterday, e);
    }
  }
}
