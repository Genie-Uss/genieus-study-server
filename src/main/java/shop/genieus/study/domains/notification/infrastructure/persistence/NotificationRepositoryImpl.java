package shop.genieus.study.domains.notification.infrastructure.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.genieus.study.domains.notification.application.repository.NotificationRepository;
import shop.genieus.study.domains.notification.domain.entity.Notification;
import shop.genieus.study.domains.notification.infrastructure.persistence.repository.NotificationJpaRepository;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {

  private final NotificationJpaRepository jpaRepository;

  @Override
  public Notification save(Notification notification) {
    return jpaRepository.save(notification);
  }
}
