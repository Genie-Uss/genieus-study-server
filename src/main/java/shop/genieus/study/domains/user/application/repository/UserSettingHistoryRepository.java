package shop.genieus.study.domains.user.application.repository;

import java.time.LocalDate;
import shop.genieus.study.domains.user.domain.entity.UserSettingHistory;

public interface UserSettingHistoryRepository {
  UserSettingHistory save(UserSettingHistory history);

  UserSettingHistory findEffectiveSettings(Long userId, LocalDate date);

  UserSettingHistory findCurrentActiveSettings(Long userId);
}
