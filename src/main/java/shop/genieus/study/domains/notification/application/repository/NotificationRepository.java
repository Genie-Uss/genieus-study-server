package shop.genieus.study.domains.notification.application.repository;

import shop.genieus.study.domains.notification.domain.entity.Notification;

public interface NotificationRepository {
  Notification save(Notification notification);
}
