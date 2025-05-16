package shop.genieus.study.domains.notification.infrastructure.client.config;

import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import shop.genieus.study.domains.notification.application.support.NotificationSender;
import shop.genieus.study.domains.notification.domain.vo.NotificationChannel;
import shop.genieus.study.domains.notification.infrastructure.client.DiscordWebhookClient;

@Configuration
public class DiscordClientConfig {

  @Bean
  public WebClient.Builder webClientBuilder() {
    return WebClient.builder();
  }

  @Bean
  public Map<NotificationChannel, NotificationSender> notificationChannelClients(
      DiscordWebhookClient discordWebhookClient) {
    return Map.of(
        NotificationChannel.DISCORD_ATTENDANCE, discordWebhookClient,
        NotificationChannel.DISCORD_ACTIVITY, discordWebhookClient);
  }
}
