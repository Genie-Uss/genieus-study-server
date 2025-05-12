package shop.genieus.study.domains.stamp.infrastructure.persistence;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import shop.genieus.study.domains.stamp.application.repository.StampHistoryRepository;
import shop.genieus.study.domains.stamp.domain.entity.StampHistory;
import shop.genieus.study.domains.stamp.infrastructure.persistence.repository.StampHistoryJpaRepository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class StampHistoryRepositoryImpl implements StampHistoryRepository {
  private final StampHistoryJpaRepository jpaRepository;

  @Override
  public StampHistory save(StampHistory stampHistory) {
    return jpaRepository.save(stampHistory);
  }

  @Override
  public StampHistory findByUserIdAndVerifiedAt(Long userId, LocalDate verifiedAt) {
    return jpaRepository.findByUserIdAndVerifiedAt(userId,verifiedAt).orElse(null);
  }
}
