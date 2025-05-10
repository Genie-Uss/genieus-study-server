package shop.genieus.study.domains.stamp.infrastructure.persistence;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import shop.genieus.study.domains.stamp.application.repository.StampRepository;
import shop.genieus.study.domains.stamp.domain.entity.Stamp;
import shop.genieus.study.domains.stamp.infrastructure.persistence.repository.StampJpaRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class StampRepositoryImpl implements StampRepository {
  private final StampJpaRepository jpaRepository;

  @Override
  public Stamp save(Stamp stamp) {
    return jpaRepository.save(stamp);
  }

  @Override
  public List<Stamp> findAllByUserIdAndVerifiedAt(Long userId, LocalDate today) {
    LocalDateTime startOfDay = today.atStartOfDay();
    LocalDateTime startOfNextDay = today.plusDays(1).atStartOfDay();
    return jpaRepository.findAllByUserIdAndVerifiedAt(userId,startOfDay,startOfNextDay);
  }

}
