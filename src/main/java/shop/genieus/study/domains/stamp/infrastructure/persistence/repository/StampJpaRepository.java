package shop.genieus.study.domains.stamp.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.genieus.study.domains.stamp.domain.entity.Stamp;

public interface StampJpaRepository extends JpaRepository<Stamp, Long> {}
