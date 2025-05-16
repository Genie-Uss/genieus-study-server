package shop.genieus.study.domains.notification.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.genieus.study.commons.notification.NotificationMessageBuilder;
import shop.genieus.study.domains.notification.application.support.NotificationProcessor;

@Service
@RequiredArgsConstructor
public class NotificationService {
  private final NotificationProcessor processor;

  public void processAndSend(NotificationMessageBuilder messageBuilder) {
    processor.process(messageBuilder);
  }

  public void shutdown() {
    processor.shutdown();
  }
}
