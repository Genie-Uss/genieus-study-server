package shop.genieus.study.domains.notification.application.support;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import shop.genieus.study.domains.notification.domain.entity.Notification;
import shop.genieus.study.domains.notification.domain.vo.NotificationChannel;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationDispatcher {

  private final Map<NotificationChannel, NotificationSender> channelClients;

  public boolean dispatch(Notification notification, String colorCode) {
    NotificationSender sender = channelClients.get(notification.getChannel());
    if (sender == null) {
      log.warn("알림 채널 Sender 없음: {}", notification.getChannel());
      return false;
    }

    try {
      return sender.send(notification, colorCode);
    } catch (Exception e) {
      log.warn("알림 전송 중 오류", e);
      return false;
    }
  }
}
