package shop.genieus.study.domains.stamp.infrastructure.persistence.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.genieus.study.domains.stamp.domain.entity.StampHistory;

public interface StampHistoryJpaRepository extends JpaRepository<StampHistory, Long> {
  Optional<StampHistory> findByUserIdAndVerifiedAt(Long userId, LocalDate verifiedAt);

  @Query("SELECT h FROM StampHistory h WHERE h.userId IN :userIds AND h.verifiedAt = :verifiedAt")
  List<StampHistory> findByUserIdsAndVerifiedAt(
      @Param("userIds") List<Long> userIds, @Param("verifiedAt") LocalDate verifiedAt);
}
