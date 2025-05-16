package shop.genieus.study.domains.notification.application.support;

import org.springframework.stereotype.Component;
import shop.genieus.study.commons.notification.NotificationMessageBuilder;

@Component
public class NotificationFormatter {

  public String format(NotificationMessageBuilder builder, String nickname) {
    String formatted = String.format(builder.buildMessage(), nickname);
    return builder.getEmoji() + " " + formatted;
  }
}
