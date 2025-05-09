package shop.genieus.study.domains.user.infrastructure.persistence.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.genieus.study.domains.user.domain.entity.User;
import shop.genieus.study.domains.user.domain.vo.Email;

public interface UserJpaRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(Email email);

  boolean existsByEmail(Email email);
}
