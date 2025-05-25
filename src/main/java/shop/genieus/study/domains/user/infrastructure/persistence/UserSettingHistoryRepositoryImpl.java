package shop.genieus.study.domains.user.infrastructure.persistence;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import shop.genieus.study.domains.user.application.exception.UserSettingHistoryNotFoundException;
import shop.genieus.study.domains.user.application.repository.UserSettingHistoryRepository;
import shop.genieus.study.domains.user.domain.entity.UserSettingHistory;
import shop.genieus.study.domains.user.infrastructure.persistence.repository.UserSettingHistoryJpaRepository;

@Repository
@RequiredArgsConstructor
public class UserSettingHistoryRepositoryImpl implements UserSettingHistoryRepository {

  private final UserSettingHistoryJpaRepository jpaRepository;

  @Override
  public UserSettingHistory save(UserSettingHistory history) {
    return jpaRepository.save(history);
  }

  @Override
  public UserSettingHistory findEffectiveSettings(Long userId, LocalDate date) {
    return jpaRepository
        .findEffectiveSettings(userId, date, PageRequest.of(0, 1))
        .orElseThrow(() -> UserSettingHistoryNotFoundException.create(date, userId));
  }

  @Override
  public UserSettingHistory findCurrentActiveSettings(Long userId) {
    return jpaRepository
        .findCurrentActiveSettings(userId)
        .orElseThrow(() -> UserSettingHistoryNotFoundException.noActiveSettings(userId));
  }
}
