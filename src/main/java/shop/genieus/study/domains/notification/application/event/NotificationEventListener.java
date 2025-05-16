package shop.genieus.study.domains.notification.application.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import shop.genieus.study.commons.notification.NotificationMessageBuilder;
import shop.genieus.study.domains.notification.application.NotificationService;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventListener {

  private final NotificationService notificationService;

  @EventListener
  public void handleNotificationEvent(NotificationMessageBuilder event) {
    try {
      notificationService.processAndSend(event);
    } catch (Exception e) {
      log.error("이벤트 처리 중 오류 발생", e);
    }
  }
}
