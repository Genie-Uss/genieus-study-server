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

    if (log.isDebugEnabled()) {
      log.debug("리프레시 토큰 저장: userId={}, tokenIdHash={}", userId, tokenValue.hashCode());
    }
  }

  @Override
  public void addToBlacklist(TokenId tokenId, Instant expiration) {
    long ttlMillis = java.time.temporal.ChronoUnit.MILLIS.between(Instant.now(), expiration);
    if (ttlMillis <= 0) {
      return;
    }

    ttlMillis += 60_000;
    tokenRedisRepository.addToBlacklist(tokenId.value(), ttlMillis);

    if (log.isDebugEnabled()) {
      log.debug("토큰 블랙리스트 등록: tokenIdHash={}, 만료시간={}", tokenId.value().hashCode(), expiration);
    }
  }

  @Override
  public void removeRefreshToken(TokenId tokenId, Long userId) {
    tokenRedisRepository.removeRefreshToken(tokenId.value(), userId);

    if (log.isDebugEnabled()) {
      log.debug("리프레시 토큰 제거: userId={}, tokenIdHash={}", userId, tokenId.value().hashCode());
    }
  }

  @Override
  public boolean isBlacklisted(TokenId tokenId) {
    boolean blacklisted = tokenRedisRepository.isBlacklisted(tokenId.value());

    if (blacklisted && log.isDebugEnabled()) {
      log.debug("블랙리스트에 존재하는 토큰: tokenIdHash={}", tokenId.value().hashCode());
    }

    return blacklisted;
  }

  @Override
  public boolean isValidRefreshToken(TokenId tokenId, String refreshToken) {
    if (tokenId == null || refreshToken == null || refreshToken.isEmpty()) {
      return false;
    }
    String storedToken = tokenRedisRepository.getRefreshToken(tokenId.value());

    boolean isValid = storedToken != null && storedToken.equals(refreshToken);

    if (!isValid && log.isDebugEnabled()) {
      log.debug("유효하지 않은 리프레시 토큰: tokenIdHash={}", tokenId.value().hashCode());
    }

    return isValid;
  }
}
