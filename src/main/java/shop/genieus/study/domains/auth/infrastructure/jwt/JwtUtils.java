package shop.genieus.study.domains.auth.infrastructure.jwt;

import io.jsonwebtoken.Claims;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.genieus.study.domains.auth.application.dto.result.TokenValidationResult;
import shop.genieus.study.domains.auth.application.util.TokenUtils;
import shop.genieus.study.domains.auth.domain.exception.TokenExpiredException;
import shop.genieus.study.domains.auth.domain.vo.TokenPair;
import shop.genieus.study.domains.auth.infrastructure.jwt.util.JwtProvider;
import shop.genieus.study.domains.auth.infrastructure.jwt.util.JwtValidator;

@Component
@RequiredArgsConstructor
public class JwtUtils implements TokenUtils {
  private final JwtProvider jwtProvider;
  private final JwtValidator jwtValidator;

  @Override
  public TokenPair createTokenPair(String tokenIdValue, Long subject) {
    String accessTokenValue = jwtProvider.createAccessToken(subject, tokenIdValue);
    String refreshTokenValue = jwtProvider.createRefreshToken(subject, tokenIdValue);

    Instant issuedAt = Instant.now();
    return TokenPair.create(tokenIdValue, accessTokenValue, refreshTokenValue, subject, issuedAt);
  }

  @Override
  public Instant getExpirationTime(String accessToken) {
    return jwtValidator.getExpirationTime(accessToken);
  }

  @Override
  public TokenValidationResult validateTokenAndExtractId(String token)
      throws TokenExpiredException {
    Claims claims = jwtValidator.validateToken(token);

    return TokenValidationResult.of(claims.getId(), Long.parseLong(claims.getSubject()));
  }
}
