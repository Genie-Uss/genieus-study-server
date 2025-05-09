package shop.genieus.study.domains.user.application;

import shop.genieus.study.domains.user.domain.PasswordService;

public interface PasswordEncryptionService extends PasswordService {
  String encode(String rawPassword);

  boolean matches(String rawPassword, String encodedPassword);
}
