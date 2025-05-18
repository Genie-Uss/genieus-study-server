package shop.genieus.study.domains.user.infrastructure.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.genieus.study.domains.user.application.exception.UserNotFoundException;
import shop.genieus.study.domains.user.application.repository.UserRepository;
import shop.genieus.study.domains.user.domain.entity.User;
import shop.genieus.study.domains.user.domain.exception.UserValidationException;
import shop.genieus.study.domains.user.domain.vo.Email;
import shop.genieus.study.domains.user.domain.vo.Nickname;
import shop.genieus.study.domains.user.infrastructure.persistence.repository.UserJpaRepository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
  private final UserJpaRepository jpaRepository;

  @Override
  public User save(User user) {
    return jpaRepository.save(user);
  }

  @Override
  public User findByEmail(String email) {
    return jpaRepository
        .findByEmail(Email.of(email))
        .orElseThrow(() -> UserValidationException.noEmailOrPassword());
  }

  @Override
  public boolean existsByEmail(String email) {
    return jpaRepository.existsByEmail(Email.of(email));
  }

  @Override
  public boolean existsByNickname(String nickname) {
    return jpaRepository.existsByNickname(Nickname.of(nickname));
  }

  @Override
  public User findById(Long userId) {
    return jpaRepository.findById(userId).orElseThrow(() -> UserNotFoundException.create());
  }
}
