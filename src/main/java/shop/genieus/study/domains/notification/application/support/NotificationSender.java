package shop.genieus.study.domains.notification.application.support;

import shop.genieus.study.domains.notification.domain.entity.Notification;

public interface NotificationSender {
  boolean send(Notification notification, String colorCode);
}
