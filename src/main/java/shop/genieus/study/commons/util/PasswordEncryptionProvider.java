package shop.genieus.study.commons.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncryptionProvider {
  private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  public String encode(CharSequence rawPassword) {
    return this.encoder.encode(rawPassword);
  }

  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    return this.encoder.matches(rawPassword, encodedPassword);
  }
}
