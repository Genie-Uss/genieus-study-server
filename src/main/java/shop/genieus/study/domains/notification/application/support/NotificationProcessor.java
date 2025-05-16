package shop.genieus.study.domains.notification.application.support;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import shop.genieus.study.commons.notification.NotificationChannelType;
import shop.genieus.study.commons.notification.NotificationMessageBuilder;
import shop.genieus.study.commons.provider.AuthProvider;
import shop.genieus.study.domains.notification.application.repository.NotificationRepository;
import shop.genieus.study.domains.notification.domain.entity.Notification;
import shop.genieus.study.domains.notification.domain.vo.NotificationChannel;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationProcessor {

  private final AuthProvider authProvider;

  private final NotificationRepository notificationRepository;
  private final NotificationDispatcher dispatcher;
  private final NotificationFormatter formatter;

  private final ExecutorService executorService = Executors.newFixedThreadPool(5);

  public void process(NotificationMessageBuilder builder) {
    executorService.submit(() -> handle(builder));
  }

  private void handle(NotificationMessageBuilder builder) {
    try {
      Long userId = builder.getUserId();
      String nickname = authProvider.getUserNickname(userId);
      NotificationChannel channel = mapChannelType(builder.getChannelType());

      String message = formatter.format(builder, nickname);
      Notification notification =
          Notification.create(userId, builder.buildTitle(), message, channel);

      boolean success = dispatcher.dispatch(notification, builder.getColorCode());
      if (success) {
        notification.markAsSent();
      } else {
        notification.markAsFailed("전송 실패");
      }

      save(notification);
    } catch (Exception e) {
      log.error("알림 처리 중 오류 발생", e);
    }
  }

  @Transactional
  void save(Notification notification) {
    try {
      notificationRepository.save(notification);
    } catch (Exception e) {
      log.error("알림 저장 중 오류", e);
    }
  }

  private NotificationChannel mapChannelType(NotificationChannelType type) {
    return switch (type) {
      case ATTENDANCE -> NotificationChannel.DISCORD_ATTENDANCE;
      case ACTIVITY -> NotificationChannel.DISCORD_ACTIVITY;
    };
  }

  public void shutdown() {
    executorService.shutdown();
  }
}
