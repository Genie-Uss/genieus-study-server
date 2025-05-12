package shop.genieus.study.domains.stamp.infrastructure.persistence;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import shop.genieus.study.domains.stamp.application.repository.StampRepository;
import shop.genieus.study.domains.stamp.domain.entity.CodingTestStamp;
import shop.genieus.study.domains.stamp.domain.entity.ResumeStamp;
import shop.genieus.study.domains.stamp.domain.entity.Stamp;
import shop.genieus.study.domains.stamp.domain.entity.TilStamp;
import shop.genieus.study.domains.stamp.domain.exception.StampNotFoundException;
import shop.genieus.study.domains.stamp.infrastructure.persistence.repository.StampJpaRepository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class StampRepositoryImpl implements StampRepository {
  private final StampJpaRepository jpaRepository;

  @Override
  public Stamp save(Stamp stamp) {
    return jpaRepository.save(stamp);
  }

  @Override
  public Stamp findById(Long id) {
    return jpaRepository
        .findById(id)
        .orElseThrow(() -> StampNotFoundException.of(id));
  }

  @Override
  public void deleteById(Long id) {
    jpaRepository.deleteById(id);
  }

  @Override
  public List<Stamp> getStampByDate(Long userId, LocalDate date) {
    LocalDateTime start = date.atStartOfDay();
    LocalDateTime end = date.plusDays(1).atStartOfDay();
    return jpaRepository.getStampByDate(userId, start, end);
  }

  @Override
  public List<CodingTestStamp> getCtStampByDate(Long userId, LocalDate date) {
    LocalDateTime start = date.atStartOfDay();
    LocalDateTime end = date.plusDays(1).atStartOfDay();
    return jpaRepository.getCtStampByDate(userId, start, end);
  }

  @Override
  public List<TilStamp> getTilStampByDate(Long userId, LocalDate date) {
    LocalDateTime start = date.atStartOfDay();
    LocalDateTime end = date.plusDays(1).atStartOfDay();
    return jpaRepository.getTilStampByDate(userId, start, end);

  }

  @Override
  public List<ResumeStamp> getResumeStampByDate(Long userId, LocalDate date) {
    LocalDateTime start = date.atStartOfDay();
    LocalDateTime end = date.plusDays(1).atStartOfDay();
    return jpaRepository.getResumeStampByDate(userId, start, end);

  }

  @Override
  public long countCtStampByDate(Long userId, LocalDate date) {
    LocalDateTime start = date.atStartOfDay();
    LocalDateTime end = date.plusDays(1).atStartOfDay();
    return jpaRepository.countCtStampByDate(userId, start, end);
  }

  @Override
  public long countTilStampByDate(Long userId, LocalDate date) {
    LocalDateTime start = date.atStartOfDay();
    LocalDateTime end = date.plusDays(1).atStartOfDay();
    return jpaRepository.countTilStampByDate(userId, start, end);
  }

  @Override
  public long countResumeStampByDate(Long userId, LocalDate date) {
    LocalDateTime start = date.atStartOfDay();
    LocalDateTime end = date.plusDays(1).atStartOfDay();
    return jpaRepository.countResumeStampByDate(userId, start, end);
  }
}
