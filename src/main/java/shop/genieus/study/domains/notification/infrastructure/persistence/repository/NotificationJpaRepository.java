package shop.genieus.study.domains.notification.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.genieus.study.domains.notification.domain.entity.Notification;

public interface NotificationJpaRepository extends JpaRepository<Notification, Long> {}
