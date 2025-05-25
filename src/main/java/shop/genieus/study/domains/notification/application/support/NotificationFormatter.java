package shop.genieus.study.domains.notification.application.support;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import shop.genieus.study.commons.notification.NotificationMessageBuilder;

@Component
public class NotificationFormatter {
  @Value("${frontend.url}")
  private String frontendUrl;

  public String format(NotificationMessageBuilder builder, String nickname) {
    String message = builder.buildMessage();

    String formatted;
    if (message.contains("%s")) {
      formatted = message.replaceFirst("%s", nickname);
    } else {
      formatted = nickname + ": " + message;
    }

    String finalMessage = builder.getEmoji() + " " + formatted;

    if (builder.requiresFrontendLink()) {
      String dynamicUrl = builder.buildFrontendUrl(frontendUrl);
      finalMessage += "\n\n　　🌐 [웹에서 보기](" + dynamicUrl + ")";
    }

    return finalMessage;
  }
}
