package shop.genieus.study.domains.notification.infrastructure.client.vo;

import java.util.List;

public record DiscordWebhookPayload(String username, List<DiscordEmbed> embeds) {
  public static DiscordWebhookPayload from(String title, String message, String colorCode) {
    return new DiscordWebhookPayload(
        "Genieus Study Bot", List.of(new DiscordEmbed(title, message, colorCode)));
  }

  public record DiscordEmbed(String title, String description, String color) {}
}
