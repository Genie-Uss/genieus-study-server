package shop.genieus.study.domains.stamp.infrastructure.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.genieus.study.domains.stamp.domain.entity.Stamp;

public interface StampJpaRepository extends JpaRepository<Stamp, Long> {
  @Query(
      "SELECT s FROM Stamp s WHERE s.userId = :userId AND s.verifiedAt >= :startOfDay AND s.verifiedAt < :endOfDay")
  List<Stamp> findAllByUserIdAndVerifiedAt(
      @Param("userId") Long userId,
      @Param("startOfDay") LocalDateTime startOfDay,
      @Param("endOfDay") LocalDateTime endOfDay);

}
