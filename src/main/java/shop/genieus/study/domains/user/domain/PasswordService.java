package shop.genieus.study.domains.user.domain;

public interface PasswordService {
  String encode(String rawPassword);

  boolean matches(String rawPassword, String encodedPassword);
}
