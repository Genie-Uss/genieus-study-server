package shop.genieus.study.domains.notification.infrastructure.client.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "discord")
public class DiscordProperties {

  private Webhook webhookUrl = new Webhook();

  @Getter
  @Setter
  public static class Webhook {
    private String attendance;
    private String activity;
  }
}
