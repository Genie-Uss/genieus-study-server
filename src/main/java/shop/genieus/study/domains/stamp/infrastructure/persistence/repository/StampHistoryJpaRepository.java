package shop.genieus.study.domains.stamp.infrastructure.persistence.repository;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.genieus.study.domains.stamp.domain.entity.StampHistory;

public interface StampHistoryJpaRepository extends JpaRepository<StampHistory, Long> {
    Optional<StampHistory> findByUserIdAndVerifiedAt(Long userId, LocalDate verifiedAt);
}
