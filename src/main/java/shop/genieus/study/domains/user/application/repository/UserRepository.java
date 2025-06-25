package shop.genieus.study.domains.user.application.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import shop.genieus.study.domains.user.application.dto.info.GetAdminUserSearchInfo;
import shop.genieus.study.domains.user.application.dto.result.GetAdminUserSearchResult;
import shop.genieus.study.domains.user.domain.entity.User;

public interface UserRepository {
  User save(User user);

  User findByEmail(String email);

  boolean existsByEmail(String email);

  boolean existsByNickname(String nickname);

  User findById(Long userId);

  List<User> findAllActiveUsers();

  List<User> findAllParticipatingUsers();

  GetAdminUserSearchResult findUsers(
      GetAdminUserSearchInfo.AdminUserFilterInfo filterInfo, Pageable pageable);
}
