package shop.genieus.study.domains.user.application;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.genieus.study.commons.provider.UserProvider;
import shop.genieus.study.commons.provider.model.UserInfo;
import shop.genieus.study.commons.provider.model.UserSettingHistoryInfo;
import shop.genieus.study.domains.user.application.dto.result.UserInfoResult;
import shop.genieus.study.domains.user.application.exception.UserNotFoundException;
import shop.genieus.study.domains.user.application.mapper.UserMapper;
import shop.genieus.study.domains.user.application.repository.UserCacheRepository;
import shop.genieus.study.domains.user.application.repository.UserRepository;
import shop.genieus.study.domains.user.application.repository.UserSettingHistoryRepository;
import shop.genieus.study.domains.user.domain.entity.User;
import shop.genieus.study.domains.user.domain.entity.UserSettingHistory;
import shop.genieus.study.domains.user.domain.exception.UserValidationException;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserQueryService implements UserProvider {
  private final UserRepository repository;
  private final UserCacheRepository userCacheRepository;
  private final UserSettingHistoryRepository settingHistoryRepository;
  private final UserMapper mapper;
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
    return mapper.from(findById(userId));
  }

  @Override
  public UserInfo validateUserCredentials(String email, String password) {
    try {
      User user = repository.findByEmail(email);

      if (!user.matchPassword(password, encryptionService)) {
        throw UserValidationException.noEmailOrPassword();
      }

      validateLoginAllowed(user);

      return mapper.from(user);
    } catch (Exception exception) {
      throw new IllegalArgumentException(exception.getMessage());
    }
  }

  @Override
  public UserSettingHistoryInfo getEffectiveSettingsByDate(Long userId, LocalDate date) {
    return mapper.from(getUserSettingHistoryByDate(userId, date));
  }

  @Override
  public List<UserInfo> getAllActiveUsers() {
    List<User> activeUsers = repository.findAllActiveUsers();
    return activeUsers.stream().map(au -> mapper.from(au)).collect(Collectors.toList());
  }

  @Override
  public List<UserInfo> getAllParticipatingUsers() {
    List<UserInfo> cachedUsers = userCacheRepository.findAllParticipatingUsers();

    if (cachedUsers != null) {
      return cachedUsers;
    }

    log.debug("참여 사용자 캐시 미스 - DB에서 조회 및 캐싱");
    List<User> participatingUsers = repository.findAllParticipatingUsers();
    List<UserInfo> users =
        participatingUsers.stream().map(mapper::from).collect(Collectors.toList());

    userCacheRepository.saveParticipatingUsers(users);
    return users;
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
}
