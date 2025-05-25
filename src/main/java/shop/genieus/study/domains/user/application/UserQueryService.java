package shop.genieus.study.domains.user.application;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.genieus.study.commons.provider.UserProvider;
import shop.genieus.study.commons.provider.dto.UserInfo;
import shop.genieus.study.commons.provider.dto.UserSettingHistoryInfo;
import shop.genieus.study.domains.user.application.dto.result.UserInfoResult;
import shop.genieus.study.domains.user.application.exception.UserNotFoundException;
import shop.genieus.study.domains.user.application.repository.UserRepository;
import shop.genieus.study.domains.user.application.repository.UserSettingHistoryRepository;
import shop.genieus.study.domains.user.domain.entity.User;
import shop.genieus.study.domains.user.domain.entity.UserSettingHistory;
import shop.genieus.study.domains.user.domain.exception.UserValidationException;
import shop.genieus.study.domains.user.domain.vo.Email;
import shop.genieus.study.domains.user.domain.vo.Nickname;
import shop.genieus.study.domains.user.domain.vo.UserSettings;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserQueryService implements UserProvider {
  private final UserRepository repository;
  private final UserSettingHistoryRepository settingHistoryRepository;
  private final PasswordEncryptionService encryptionService;

  public boolean checkNicknameAvailable(String nickname) {
    boolean exist = repository.existsByNickname(nickname);
    return !exist;
  }

  public boolean checkEmailAvailable(String email) {
    boolean exist = repository.existsByEmail(email);
    return !exist;
  }

  public UserInfoResult getUserInfo(Long userId) {
    User user = findById(userId);
    return new UserInfoResult(user.getId(), user.getNickname().getValue());
  }

  @Override
  public UserInfo findByUserId(Long userId) throws UserNotFoundException {
    return from(findById(userId));
  }

  @Override
  public UserInfo validateUserCredentials(String email, String password) {
    try {
      User user = repository.findByEmail(email);

      if (!user.matchPassword(password, encryptionService)) {
        throw UserValidationException.noEmailOrPassword();
      }

      validateLoginAllowed(user);

      return from(user);
    } catch (Exception exception) {
      throw new IllegalArgumentException(exception.getMessage());
    }
  }

  @Override
  public UserSettingHistoryInfo getEffectiveSettingsByDate(Long userId, LocalDate date) {
    return from(getUserSettingHistoryByDate(userId, date));
  }

  private User findById(Long userId) {
    return repository.findById(userId);
  }

  private void validateLoginAllowed(User user) {
    if (!user.canLogin()) {
      if (user.isPending()) {
        throw UserValidationException.accountPending();
      } else if (user.isRejected()) {
        throw UserValidationException.accountRejected();
      } else if (user.isInactive()) {
        throw UserValidationException.accountInactive();
      } else if (user.isLocked()) {
        throw UserValidationException.accountLocked();
      }
      throw UserValidationException.accountNotApproved();
    }
  }

  private UserSettingHistory getUserSettingHistoryByDate(Long userId, LocalDate date) {
    return settingHistoryRepository.findEffectiveSettings(userId, date);
  }

  private UserInfo from(User user) {
    UserSettings currentUserSettings = user.getCurrentSettings();
    return new UserInfo(
        user.getId(),
        getValueOrNull(user.getEmail(), Email::getValue),
        getValueOrNull(user.getNickname(), Nickname::getValue),
        user.getProfileImage(),
        getValueOrNull(user.getRole(), Enum::name),
        getValueOrNull(currentUserSettings, UserSettings::getDesiredCheckInTime),
        getValueOrNull(currentUserSettings, UserSettings::getDesiredCoreTime),
        user.getIsActive());
  }

  private UserSettingHistoryInfo from(UserSettingHistory history) {
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
