package shop.genieus.study.domains.user.infrastructure.persistence;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import shop.genieus.study.domains.user.application.dto.info.AdminUserInfo;
import shop.genieus.study.domains.user.application.dto.info.GetAdminUserSearchInfo;
import shop.genieus.study.domains.user.application.dto.result.GetAdminUserSearchResult;
import shop.genieus.study.domains.user.application.dto.result.PageInfo;
import shop.genieus.study.domains.user.application.exception.UserNotFoundException;
import shop.genieus.study.domains.user.application.repository.UserRepository;
import shop.genieus.study.domains.user.domain.entity.User;
import shop.genieus.study.domains.user.domain.exception.UserValidationException;
import shop.genieus.study.domains.user.domain.vo.Email;
import shop.genieus.study.domains.user.domain.vo.Nickname;
import shop.genieus.study.domains.user.infrastructure.persistence.repository.UserJpaRepository;
import shop.genieus.study.domains.user.infrastructure.persistence.repository.UserSearchRepository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
  private final UserJpaRepository jpaRepository;
  private final UserSearchRepository searchRepository;

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

  @Override
  public List<User> findAllActiveUsers() {
    return jpaRepository.findAllActiveUsers();
  }

  @Override
  public List<User> findAllParticipatingUsers() {
    return jpaRepository.findAllParticipatingUsers();
  }

  @Override
  public GetAdminUserSearchResult findUsers(
      GetAdminUserSearchInfo.AdminUserFilterInfo filterInfo, Pageable pageable) {
    Page<AdminUserInfo> userPage = searchRepository.findUsers(filterInfo, pageable);
    PageInfo pageInfo =
        new PageInfo(
            userPage.getNumber(),
            userPage.getSize(),
            userPage.getTotalElements(),
            userPage.getTotalPages(),
            userPage.isLast());

    return new GetAdminUserSearchResult(userPage.getContent(), pageInfo);
  }
}
