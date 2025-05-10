package shop.genieus.study.commons.provider;

import shop.genieus.study.commons.provider.dto.UserInfo;

public interface UserProvider {
  UserInfo findByUserId(Long userId);

  UserInfo validateUserCredentials(String email, String password);
}
