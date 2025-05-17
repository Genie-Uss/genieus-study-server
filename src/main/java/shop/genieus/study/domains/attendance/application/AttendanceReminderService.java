package shop.genieus.study.domains.attendance.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import shop.genieus.study.domains.attendance.application.event.CheckInReminderEvent;
import shop.genieus.study.domains.attendance.application.event.CheckOutReminderEvent;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendanceReminderService {

  private final ApplicationEventPublisher eventPublisher;

  @Value("${server.url}")
  private String serverUrl;

  @Scheduled(cron = "${scheduler.notification.check-in-cron}")
  public void sendCheckInReminder() {
    log.info("출석 알림 이벤트 발행 시작");
    CheckInReminderEvent event = new CheckInReminderEvent(serverUrl);
    eventPublisher.publishEvent(event);
    log.info("출석 알림 이벤트 발행 완료");
  }

  @Scheduled(cron = "${scheduler.notification.check-out-cron}")
  public void sendCheckOutReminder() {
    log.info("코어 시간 종료 알림 이벤트 발행 시작");
    CheckOutReminderEvent event = new CheckOutReminderEvent(serverUrl);
    eventPublisher.publishEvent(event);
    log.info("코어 시간 종료 알림 이벤트 발행 완료");
  }
}
