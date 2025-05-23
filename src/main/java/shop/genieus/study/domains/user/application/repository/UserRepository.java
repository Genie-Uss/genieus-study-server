package shop.genieus.study.domains.user.application.repository;

import shop.genieus.study.domains.user.domain.entity.User;

public interface UserRepository {
  User save(User user);

  User findByEmail(String email);

  boolean existsByEmail(String email);

  boolean existsByNickname(String nickname);

  User findById(Long userId);
}
