package shop.genieus.study.domains.user.application;

import java.util.Optional;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.genieus.study.commons.provider.UserProvider;
import shop.genieus.study.commons.provider.dto.UserInfo;
import shop.genieus.study.domains.user.application.dto.info.SignupUserInfo;
import shop.genieus.study.domains.user.application.exception.UserNotFoundException;
import shop.genieus.study.domains.user.application.repository.UserRepository;
import shop.genieus.study.domains.user.domain.entity.User;
import shop.genieus.study.domains.user.domain.exception.UserValidationException;
import shop.genieus.study.domains.user.domain.vo.Email;
import shop.genieus.study.domains.user.domain.vo.Nickname;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserProvider {
  private final UserRepository repository;
  private final PasswordEncryptionService encryptionService;

  public User signupUser(SignupUserInfo info) {
    this.isSamePasswordAndPasswordConfirm(info);
    this.isEmailAlreadyRegistered(info);

    return repository.save(
        User.create(info.email(), info.password(), encryptionService, info.nickname()));
  }

  public User findById(Long userId) {
    return repository.findById(userId);
  }

  public boolean checkNicknameAvailable(String nickname) {
    boolean exist = repository.existsByNickname(nickname);
    return !exist;
  }

  public boolean checkEmailAvailable(String email) {
    boolean exist = repository.existsByEmail(email);
    return !exist;
  }

  private void isSamePasswordAndPasswordConfirm(SignupUserInfo info) {
    if (!info.password().equals(info.confirmPassword())) {
      throw UserValidationException.noPasswordConfirm();
    }
  }

  private void isEmailAlreadyRegistered(SignupUserInfo info) {
    String email = info.email();
    if (repository.existsByEmail(email)) {
      throw UserValidationException.duplicateEmail(email);
    }
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
      return from(user);
    } catch (Exception exception) {
      throw new IllegalArgumentException(exception.getMessage());
    }
  }

  private UserInfo from(User user) {
    return new UserInfo(
        user.getId(),
        getValueOrNull(user.getEmail(), Email::getValue),
        getValueOrNull(user.getNickname(), Nickname::getValue),
        user.getProfileImage(),
        getValueOrNull(user.getRole(), Enum::name),
        user.getDesiredCheckInTime(),
        user.getDesiredCoreTime(),
        user.getIsActive());
  }

  private <T, R> R getValueOrNull(T obj, Function<T, R> getter) {
    return Optional.ofNullable(obj).map(getter).orElse(null);
  }
}
