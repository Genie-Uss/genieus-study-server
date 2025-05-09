package shop.genieus.study.domains.auth.infrastructure.id;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.genieus.study.domains.auth.application.util.IdGenerator;
import shop.genieus.study.domains.auth.infrastructure.id.util.UuidGenerator;

@Component
@RequiredArgsConstructor
public class IdGeneratorImpl implements IdGenerator {
  private final UuidGenerator uuidGenerator;

  @Override
  public String generateUniqueId() {
    return uuidGenerator.generate();
  }
}
