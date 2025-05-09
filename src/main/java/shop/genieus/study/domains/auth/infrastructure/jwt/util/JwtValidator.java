package shop.genieus.study.domains.auth.infrastructure.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.time.Instant;
import java.util.Map;
import java.util.function.Supplier;
import org.springframework.stereotype.Component;
import shop.genieus.study.domains.auth.domain.exception.TokenExpiredException;
import shop.genieus.study.domains.auth.domain.exception.TokenParsingException;
import shop.genieus.study.domains.auth.infrastructure.jwt.config.JwtConfig;

@Component
public class JwtValidator extends AbstractJwtSupport {
  private static final Map<Class<? extends Exception>, Supplier<RuntimeException>> ERROR_MAP =
      Map.of(
          SecurityException.class, TokenParsingException::invalidSignature,
          MalformedJwtException.class, TokenParsingException::malformedToken,
          UnsupportedJwtException.class, TokenParsingException::unsupportedToken,
          IllegalArgumentException.class, TokenParsingException::emptyClaims);

  public JwtValidator(JwtConfig jwtConfig) {
    super(jwtConfig);
  }

  public Claims validateToken(String token) {
    try {
      return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
    } catch (ExpiredJwtException e) {
      throw TokenExpiredException.create();
    } catch (Exception e) {
      Supplier<RuntimeException> exceptionSupplier =
          ERROR_MAP.getOrDefault(e.getClass(), TokenParsingException::generalError);
      throw exceptionSupplier.get();
    }
  }

  public Instant getExpirationTime(String token) {
    try {
      Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
      return claims.getExpiration().toInstant();
    } catch (Exception exception) {
      return Instant.EPOCH;
    }
  }
}
