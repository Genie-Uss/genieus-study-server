package shop.genieus.study.domains.auth.infrastructure.jwt.util;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import shop.genieus.study.domains.auth.domain.vo.TokenType;
import shop.genieus.study.domains.auth.infrastructure.jwt.config.JwtConfig;
import shop.genieus.study.domains.user.domain.entity.Nickname;
import shop.genieus.study.domains.user.domain.entity.UserRole;

@Component
public class JwtProvider extends AbstractJwtSupport {
  protected static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

  public JwtProvider(JwtConfig jwtConfig) {
    super(jwtConfig);
  }

  public JwtBuilder createToken(Long subject, long expirationMs) {
    Date now = new Date();
    Date tokenExpiry = Date.from(Instant.now().plus(Duration.ofMillis(expirationMs)));

    return Jwts.builder()
        .setSubject(String.valueOf(subject))
        .setIssuedAt(now)
        .setExpiration(tokenExpiry)
        .signWith(key, SIGNATURE_ALGORITHM);
  }

  public String createAccessToken(@NonNull Long subject, String jti) {
    return this.createToken(subject, getAccessExpirationMs()).setId(jti).compact();
  }

  public String createRefreshToken(@NonNull Long subject, String jti) {
    return this.createToken(subject, getRefreshTokenExpirationMs()).setId(jti).compact();
  }

  private long getAccessExpirationMs() {
    return this.getAccessExpirationMs(TokenType.ACCESS);
  }

  private long getRefreshTokenExpirationMs() {
    return this.getAccessExpirationMs(TokenType.REFRESH);
  }

  private long getAccessExpirationMs(TokenType type) {
    return type.getExpiration().toMillis();
  }
}
