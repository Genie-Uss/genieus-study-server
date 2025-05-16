package shop.genieus.study.domains.auth.infrastructure.jwt;

import io.jsonwebtoken.Claims;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import shop.genieus.study.domains.auth.application.dto.result.TokenValidationResult;
import shop.genieus.study.domains.auth.application.util.TokenUtils;
import shop.genieus.study.domains.auth.domain.exception.TokenExpiredException;
import shop.genieus.study.domains.auth.domain.vo.TokenPair;
import shop.genieus.study.domains.auth.infrastructure.jwt.util.JwtProvider;
import shop.genieus.study.domains.auth.infrastructure.jwt.util.JwtValidator;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils implements TokenUtils {
  private final JwtProvider jwtProvider;
  private final JwtValidator jwtValidator;

  @Override
  public TokenPair createTokenPair(String tokenIdValue, Long subject) {
    if (tokenIdValue == null || subject == null) {
      throw new IllegalArgumentException("토큰 ID와 사용자 ID는 필수입니다.");
    }

    String accessTokenValue = jwtProvider.createAccessToken(subject, tokenIdValue);
    String refreshTokenValue = jwtProvider.createRefreshToken(subject, tokenIdValue);

    Instant issuedAt = Instant.now();
    TokenPair tokenPair =
        TokenPair.create(tokenIdValue, accessTokenValue, refreshTokenValue, subject, issuedAt);

    if (log.isDebugEnabled()) {
      log.debug("토큰 페어 생성 완료 - 사용자: {}, 발급시각: {}", subject, issuedAt);
    }

    return tokenPair;
  }

  @Override
  public Instant getExpirationTime(String accessToken) {
    return jwtValidator.getExpirationTime(accessToken);
  }

  @Override
  public TokenValidationResult validateTokenAndExtractId(String token)
      throws TokenExpiredException {
    if (token == null || token.isBlank()) {
      throw new IllegalArgumentException("토큰 값은 필수입니다.");
    }

    Claims claims = jwtValidator.validateToken(token);

    if (claims.getSubject() == null) {
      throw new IllegalArgumentException("토큰에 사용자 정보가 없습니다.");
    }

    try {
      Long userId = Long.parseLong(claims.getSubject());
      TokenValidationResult result = TokenValidationResult.of(claims.getId(), userId);

      if (log.isDebugEnabled()) {
        log.debug("토큰 검증 성공 - 사용자: {}, 토큰ID: {}", userId, claims.getId());
      }

      return result;
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("토큰의 사용자 ID가 유효하지 않습니다.");
    }
  }
}
