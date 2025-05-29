package shop.genieus.study.domains.user.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.genieus.study.domains.user.domain.entity.User;
import shop.genieus.study.domains.user.domain.vo.Email;
import shop.genieus.study.domains.user.domain.vo.Nickname;

public interface UserJpaRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(Email email);

  boolean existsByEmail(Email email);

  boolean existsByNickname(Nickname nickname);

  @Query("SELECT u FROM User u WHERE u.isActive = true AND u.status = 'APPROVED'")
  List<User> findAllActiveUsers();

  @Query(
      "SELECT u FROM User u WHERE u.isActive = true AND u.status = 'APPROVED' AND u.currentSettings.participationStatus = 'ACTIVE'")
  List<User> findAllParticipatingUsers();
}
