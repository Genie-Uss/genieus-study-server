package shop.genieus.study.domains.auth.infrastructure.jwt.util;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import shop.genieus.study.domains.auth.domain.vo.TokenType;
import shop.genieus.study.domains.auth.infrastructure.jwt.config.JwtConfig;

@Slf4j
@Component
public class JwtProvider extends AbstractJwtSupport {
  protected static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

  public JwtProvider(JwtConfig jwtConfig) {
    super(jwtConfig);
  }

  public JwtBuilder createToken(Long subject, long expirationMs) {
    if (subject == null) {
      throw new IllegalArgumentException("subject는 필수입니다.");
    }

    if (expirationMs <= 0) {
      throw new IllegalArgumentException("만료 시간은 0보다 커야 합니다.");
    }

    Date now = new Date();
    Date tokenExpiry = Date.from(Instant.now().plus(Duration.ofMillis(expirationMs)));

    return Jwts.builder()
        .setSubject(String.valueOf(subject))
        .setIssuedAt(now)
        .setExpiration(tokenExpiry)
        .signWith(key, SIGNATURE_ALGORITHM);
  }

  public String createAccessToken(@NonNull Long subject, String jti) {
    if (jti == null || jti.isBlank()) {
      throw new IllegalArgumentException("JWT ID(jti)는 필수입니다.");
    }

    try {
      String token = this.createToken(subject, getAccessExpirationMs()).setId(jti).compact();

      if (log.isDebugEnabled()) {
        log.debug("액세스 토큰 생성 - 사용자: {}, 토큰ID 해시: {}", subject, jti.hashCode());
      }

      return token;
    } catch (Exception e) {
      log.error("액세스 토큰 생성 실패 - 사용자: {}, 오류: {}", subject, e.getMessage());
      throw new RuntimeException("액세스 토큰 생성 중 오류가 발생했습니다.", e);
    }
  }

  public String createRefreshToken(@NonNull Long subject, String jti) {
    if (jti == null || jti.isBlank()) {
      throw new IllegalArgumentException("JWT ID(jti)는 필수입니다.");
    }

    try {
      String token = this.createToken(subject, getRefreshTokenExpirationMs()).setId(jti).compact();

      if (log.isDebugEnabled()) {
        log.debug("리프레시 토큰 생성 - 사용자: {}, 토큰ID 해시: {}", subject, jti.hashCode());
      }

      return token;
    } catch (Exception e) {
      log.error("리프레시 토큰 생성 실패 - 사용자: {}, 오류: {}", subject, e.getMessage());
      throw new RuntimeException("리프레시 토큰 생성 중 오류가 발생했습니다.", e);
    }
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
