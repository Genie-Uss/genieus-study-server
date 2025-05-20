package shop.genieus.study.domains.notification.application.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import shop.genieus.study.commons.notification.NotificationMessageBuilder;
import shop.genieus.study.domains.notification.application.NotificationService;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventListener {

  private final NotificationService notificationService;

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
  public void handleNotificationEvent(NotificationMessageBuilder event) {
    try {
      log.info("알림 이벤트 처리");
      notificationService.processAndSend(event);
    } catch (Exception e) {
      log.error("알림 이벤트 처리 중 오류 발생", e);
    }
  }
}
