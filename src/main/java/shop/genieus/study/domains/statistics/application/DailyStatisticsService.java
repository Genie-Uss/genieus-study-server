package shop.genieus.study.domains.statistics.application;

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
import shop.genieus.study.commons.provider.model.AttendanceInfo;
import shop.genieus.study.commons.provider.model.StampHistoryInfo;
import shop.genieus.study.commons.provider.model.UserInfo;
import shop.genieus.study.domains.statistics.application.assembler.DailyStatisticsAssembler;
import shop.genieus.study.domains.statistics.application.event.internal.model.DailyStampStatisticsEvent;
import shop.genieus.study.domains.statistics.domain.vo.UserStatistics;

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
      List<UserInfo> participatingUsers = userProvider.getAllParticipatingUsers();
      if (participatingUsers.isEmpty()) {
        log.info("통계에 포함될 활성 참여자가 없어 통계 생성을 건너뜁니다.");
        return;
      }

      Map<Long, String> userNicknameMap = new HashMap<>();
      List<Long> userIds = new ArrayList<>();
      for (UserInfo userInfo : participatingUsers) {
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

      DailyStampStatisticsEvent event = new DailyStampStatisticsEvent(yesterday, userStatistics);
      eventPublisher.publishEvent(event);

      log.info("일일 통계 알림 발송 완료: {} ({} users)", yesterday, userStatistics.size());
    } catch (Exception e) {
      log.warn("일일 통계 처리 중 오류 발생: {}", yesterday, e);
    }
  }
}
