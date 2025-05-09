package shop.genieus.study.domains.auth.infrastructure.jwt.util;

import java.security.Key;
import lombok.RequiredArgsConstructor;
import shop.genieus.study.domains.auth.infrastructure.jwt.config.JwtConfig;

@RequiredArgsConstructor
public abstract class AbstractJwtSupport {
  protected final Key key;

  public AbstractJwtSupport(JwtConfig jwtConfig) {
    this.key = jwtConfig.getKey();
  }
}
