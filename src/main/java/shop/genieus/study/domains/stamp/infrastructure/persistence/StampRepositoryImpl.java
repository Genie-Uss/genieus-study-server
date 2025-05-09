package shop.genieus.study.domains.stamp.infrastructure.persistence;

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
  
}
