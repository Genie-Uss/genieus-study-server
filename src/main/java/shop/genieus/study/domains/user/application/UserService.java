package shop.genieus.study.domains.user.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.genieus.study.domains.user.application.dto.info.RegisterUserInfo;
import shop.genieus.study.domains.user.application.repository.UserRepository;
import shop.genieus.study.domains.user.domain.entity.User;
import shop.genieus.study.domains.user.domain.exception.UserNotFoundException;
import shop.genieus.study.domains.user.domain.exception.UserValidationException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
  private final UserRepository repository;
  private final PasswordEncryptionService encryptionService;

  public User registerUser(RegisterUserInfo info) {
    this.isSamePasswordAndPasswordConfirm(info);
    this.isEmailAlreadyRegistered(info);

    return repository.save(
        User.create(info.email(), info.password(), encryptionService, info.nickname()));
  }

  public User validateUserCredentials(String email, String password) {
    try {
      User user = repository.findByEmail(email);
      user.matchPassword(password, encryptionService);
      return user;
    } catch (Exception exception) {
      throw new IllegalArgumentException(exception.getMessage());
    }
  }

  public User findById(Long userId) throws UserNotFoundException {
    return repository.findById(userId);
  }

  private void isSamePasswordAndPasswordConfirm(RegisterUserInfo info) {
    if (!info.password().equals(info.confirmPassword())) {
      throw UserValidationException.noPasswordConfirm();
    }
  }

  private void isEmailAlreadyRegistered(RegisterUserInfo info) {
    String email = info.email();
    if (repository.existsByEmail(email)) {
      throw UserValidationException.duplicateEmail(email);
    }
  }
}
