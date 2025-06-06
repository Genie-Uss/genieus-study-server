package shop.genieus.study.domains.user.application.repository;

import java.util.List;
import shop.genieus.study.commons.provider.model.UserInfo;

public interface UserCacheRepository {
  List<UserInfo> findAllParticipatingUsers();

  void saveParticipatingUsers(List<UserInfo> users);

  void invalidateParticipatingUsers();
}
