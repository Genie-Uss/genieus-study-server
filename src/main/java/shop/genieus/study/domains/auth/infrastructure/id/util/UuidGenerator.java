package shop.genieus.study.domains.auth.infrastructure.id.util;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class UuidGenerator {
  public String generate() {
    return UUID.randomUUID().toString();
  }
}
