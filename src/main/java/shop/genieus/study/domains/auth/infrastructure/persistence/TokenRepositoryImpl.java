package shop.genieus.study.domains.auth.infrastructure.persistence;

import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import shop.genieus.study.domains.auth.application.repository.TokenRepository;
import shop.genieus.study.domains.auth.domain.vo.TokenCredential;
import shop.genieus.study.domains.auth.domain.vo.TokenId;
import shop.genieus.study.domains.auth.infrastructure.persistence.repository.TokenRedisRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {
  private final TokenRedisRepository tokenRedisRepository;

  @Override
  public void saveRefreshToken(
      TokenId tokenId, Long userId, TokenCredential refreshTokenCredential) {
    long ttlMillis = refreshTokenCredential.tokenType().getExpiration().toMillis();
    String tokenValue = tokenId.value();
    tokenRedisRepository.saveRefreshToken(
        tokenValue, userId, refreshTokenCredential.tokenValue(), ttlMillis);
    log.info("리프레시 토큰 저장: userId={}, tokenId={}", userId, tokenValue);
  }

  @Override
  public void addToBlacklist(TokenId tokenId, Instant expiration) {
    long ttlMillis = java.time.temporal.ChronoUnit.MILLIS.between(Instant.now(), expiration);
    if (ttlMillis <= 0) {
      return;
    }
    ttlMillis += 60_000;
    tokenRedisRepository.addToBlacklist(tokenId.value(), ttlMillis);
  }

  @Override
  public void removeRefreshToken(TokenId tokenId, Long userId) {
    tokenRedisRepository.removeRefreshToken(tokenId.value(), userId);
  }

  @Override
  public boolean isBlacklisted(TokenId tokenId) {
    return tokenRedisRepository.isBlacklisted(tokenId.value());
  }

  @Override
  public boolean isValidRefreshToken(TokenId userId, String refreshToken) {
    if (userId == null || refreshToken == null || refreshToken.isEmpty()) {
      return false;
    }
    String storedToken = tokenRedisRepository.getRefreshToken(userId.value());

    return storedToken != null && storedToken.equals(refreshToken);
  }
}
