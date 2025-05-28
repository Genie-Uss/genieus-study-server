package shop.genieus.study.commons.provider;

import java.time.LocalDate;
import java.util.List;
import shop.genieus.study.commons.provider.dto.UserInfo;
import shop.genieus.study.commons.provider.dto.UserSettingHistoryInfo;

public interface UserProvider {
  UserInfo findByUserId(Long userId);

  UserInfo validateUserCredentials(String email, String password);

  UserSettingHistoryInfo getEffectiveSettingsByDate(Long userId, LocalDate date);

  List<UserInfo> getAllActiveUsers();
}
