package shop.genieus.study.domains.user.infrastructure.persistence.repository;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.genieus.study.domains.user.domain.entity.UserSettingHistory;

public interface UserSettingHistoryJpaRepository extends JpaRepository<UserSettingHistory, Long> {
  @Query(
      "SELECT h FROM UserSettingHistory h "
          + "WHERE h.userId = :userId "
          + "AND h.effectiveFromDate <= :date "
          + "AND (h.effectiveToDate IS NULL OR h.effectiveToDate >= :date) "
          + "ORDER BY h.effectiveFromDate DESC "
          + "LIMIT 1")
  Optional<UserSettingHistory> findEffectiveSettings(
      @Param("userId") Long userId, @Param("date") LocalDate date);

  @Query(
      "SELECT h FROM UserSettingHistory h "
          + "WHERE h.userId = :userId "
          + "AND h.isActive = true "
          + "AND h.effectiveToDate IS NULL "
          + "ORDER BY h.effectiveFromDate DESC")
  Optional<UserSettingHistory> findCurrentActiveSettings(@Param("userId") Long userId);
}
