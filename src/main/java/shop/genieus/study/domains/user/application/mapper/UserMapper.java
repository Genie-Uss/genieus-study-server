package shop.genieus.study.domains.user.application.mapper;

import java.util.Optional;
import java.util.function.Function;
import org.springframework.stereotype.Component;
import shop.genieus.study.commons.provider.dto.UserInfo;
import shop.genieus.study.commons.provider.dto.UserSettingHistoryInfo;
import shop.genieus.study.domains.user.domain.entity.User;
import shop.genieus.study.domains.user.domain.entity.UserSettingHistory;
import shop.genieus.study.domains.user.domain.vo.Email;
import shop.genieus.study.domains.user.domain.vo.Nickname;
import shop.genieus.study.domains.user.domain.vo.UserSettings;

@Component
public class UserMapper {
  public UserInfo from(User user) {
    UserSettings currentUserSettings = user.getCurrentSettings();
    return new UserInfo(
        user.getId(),
        getValueOrNull(user.getEmail(), Email::getValue),
        getValueOrNull(user.getNickname(), Nickname::getValue),
        user.getProfileImage(),
        getValueOrNull(user.getRole(), Enum::name),
        getValueOrNull(currentUserSettings, UserSettings::getDesiredCheckInTime),
        getValueOrNull(currentUserSettings, UserSettings::getDesiredCoreTime),
        user.getIsActive(),
        currentUserSettings.getParticipationStatus().name());
  }

  public UserSettingHistoryInfo from(UserSettingHistory history) {
    return new UserSettingHistoryInfo(
        history.getId(),
        history.getUserId(),
        history.getEffectiveFromDate(),
        history.getEffectiveToDate(),
        history.getDesiredCheckInTime(),
        history.getDesiredCoreTime(),
        history.isActive());
  }

  private <T, R> R getValueOrNull(T obj, Function<T, R> getter) {
    return Optional.ofNullable(obj).map(getter).orElse(null);
  }
}
