package shop.genieus.study.domains.notification.infrastructure.client;

import java.util.concurrent.atomic.AtomicBoolean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.genieus.study.domains.notification.application.support.NotificationSender;
import shop.genieus.study.domains.notification.domain.entity.Notification;
import shop.genieus.study.domains.notification.domain.vo.NotificationChannel;
import shop.genieus.study.domains.notification.infrastructure.client.config.DiscordProperties;
import shop.genieus.study.domains.notification.infrastructure.client.vo.DiscordWebhookPayload;

@Slf4j
@Component
@RequiredArgsConstructor
public class DiscordWebhookClient implements NotificationSender {

  private final WebClient.Builder webClientBuilder;
  private final DiscordProperties discordProperties;

  @Override
  public boolean send(Notification notification, String colorCode) {
    NotificationChannel channel = notification.getChannel();
    if (!isDiscordChannel(channel)) {
      log.warn("Discord 클라이언트에 잘못된 채널 유형이 제공되었습니다: {}", channel);
      return false;
    }

    String webhookUrl = getWebhookUrl(channel);
    if (webhookUrl.isEmpty()) {
      log.error("설정된 웹훅 URL이 없습니다: {}", channel);
      return false;
    }

    try {
      DiscordWebhookPayload payload = createPayload(notification, colorCode);

      AtomicBoolean success = new AtomicBoolean(false);

      webClientBuilder
          .build()
          .post()
          .uri(webhookUrl)
          .contentType(MediaType.APPLICATION_JSON)
          .bodyValue(payload)
          .retrieve()
          .toBodilessEntity()
          .doOnSuccess(
              response -> {
                log.debug("Discord 알림 전송 성공: {}", channel);
                success.set(true);
              })
          .doOnError(
              error -> {
                log.error("Discord 알림 전송 실패: {}, 오류: {}", channel, error.getMessage());
                success.set(false);
              })
          .block(); // 동기적으로 처리

      return success.get();
    } catch (Exception e) {
      log.error("Discord 알림 전송 중 오류 발생", e);
      return false;
    }
  }

  private boolean isDiscordChannel(NotificationChannel channel) {
    return channel == NotificationChannel.DISCORD_ATTENDANCE
        || channel == NotificationChannel.DISCORD_ACTIVITY;
  }

  private String getWebhookUrl(NotificationChannel channel) {
    return switch (channel) {
      case DISCORD_ATTENDANCE -> discordProperties.getWebhookUrl().getAttendance();
      case DISCORD_ACTIVITY -> discordProperties.getWebhookUrl().getActivity();
      default -> "";
    };
  }

  private DiscordWebhookPayload createPayload(Notification notification, String colorCode) {
    return DiscordWebhookPayload.from(
        notification.getTitle(), notification.getMessage(), colorCode);
  }
}
