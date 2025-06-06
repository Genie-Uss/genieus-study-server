package shop.genieus.study.domains.notification.application.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import shop.genieus.study.commons.event.DomainEvent;
import shop.genieus.study.commons.event.DomainEventType;
import shop.genieus.study.commons.event.EventFilter;
import shop.genieus.study.commons.notification.NotificationMessageBuilder;
import shop.genieus.study.domains.notification.application.NotificationService;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationExternalEventListener {

  private final NotificationService notificationService;

  @EventFilter({
    DomainEventType.REMIND_CHECK_IN,
    DomainEventType.REMIND_CHECK_OUT,
    DomainEventType.CHECK_IN,
    DomainEventType.CHECK_OUT,
    DomainEventType.STAMP_CREATED,
    DomainEventType.DAILY_STATS_CALCULATED,
  })
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
  public void handleNotificationEvent(DomainEvent event) {
    try {
      NotificationMessageBuilder builder = (NotificationMessageBuilder) event;
      notificationService.processAndSend(builder);
    } catch (Exception e) {
      log.error("알림 이벤트 처리 중 오류 발생", e);
    }
  }
}
