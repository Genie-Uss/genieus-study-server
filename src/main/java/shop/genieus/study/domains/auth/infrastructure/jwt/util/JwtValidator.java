package shop.genieus.study.domains.auth.infrastructure.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.time.Instant;
import org.springframework.stereotype.Component;
import shop.genieus.study.domains.auth.domain.exception.TokenExpiredException;
import shop.genieus.study.domains.auth.domain.exception.TokenParsingException;
import shop.genieus.study.domains.auth.infrastructure.jwt.config.JwtConfig;

@Component
public class JwtValidator extends AbstractJwtSupport {

  public JwtValidator(JwtConfig jwtConfig) {
    super(jwtConfig);
  }

  public Claims validateToken(String token) {
    if (token == null || token.isBlank()) {
      throw TokenParsingException.malformedToken();
    }

    try {
      return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
    } catch (ExpiredJwtException e) {
      throw TokenExpiredException.create();
    } catch (Exception e) {
      if (e instanceof SecurityException) {
        throw TokenParsingException.invalidSignature();
      } else if (e instanceof MalformedJwtException) {
        throw TokenParsingException.malformedToken();
      } else if (e instanceof UnsupportedJwtException) {
        throw TokenParsingException.unsupportedToken();
      } else if (e instanceof IllegalArgumentException) {
        throw TokenParsingException.emptyClaims();
      }
      throw TokenParsingException.generalError();
    }
  }

  public Instant getExpirationTime(String token) {
    if (token == null || token.isBlank()) {
      return Instant.EPOCH;
    }

    try {
      Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
      return claims.getExpiration().toInstant();
    } catch (Exception exception) {
      return Instant.EPOCH;
    }
  }
}
