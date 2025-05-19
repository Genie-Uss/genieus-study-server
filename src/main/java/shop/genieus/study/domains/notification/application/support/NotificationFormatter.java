package shop.genieus.study.domains.notification.application.support;

import org.springframework.stereotype.Component;
import shop.genieus.study.commons.notification.NotificationMessageBuilder;

@Component
public class NotificationFormatter {

  public String format(NotificationMessageBuilder builder, String nickname) {
    String message = builder.buildMessage();

    String formatted;
    if (message.contains("%s")) {
      formatted = message.replaceFirst("%s", nickname);
    } else {
      formatted = nickname + ": " + message;
    }

    return builder.getEmoji() + " " + formatted;
  }
}
