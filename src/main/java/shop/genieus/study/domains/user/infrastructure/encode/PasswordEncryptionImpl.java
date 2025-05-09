package shop.genieus.study.domains.user.infrastructure.encode;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.genieus.study.commons.util.PasswordEncryptionProvider;
import shop.genieus.study.domains.user.application.PasswordEncryptionService;

@Component
@RequiredArgsConstructor
public class PasswordEncryptionImpl implements PasswordEncryptionService {
  private final PasswordEncryptionProvider provider;

  @Override
  public String encode(String rawPassword) {
    return provider.encode(rawPassword);
  }

  @Override
  public boolean matches(String rawPassword, String encodedPassword) {
    return provider.matches(rawPassword, encodedPassword);
  }
}
