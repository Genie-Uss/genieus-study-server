package shop.genieus.study.domains.stamp.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.genieus.study.domains.stamp.domain.entity.StampView;

public interface StampViewJpaRepository extends JpaRepository<StampView, Long> {}
