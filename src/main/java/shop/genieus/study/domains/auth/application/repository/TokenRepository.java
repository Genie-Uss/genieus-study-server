package shop.genieus.study.domains.auth.application.repository;

import shop.genieus.study.domains.auth.domain.vo.TokenCredential;
import shop.genieus.study.domains.auth.domain.vo.TokenId;

import java.time.Instant;

public interface TokenRepository {
  void saveRefreshToken(TokenId tokenId, Long userId, TokenCredential refreshTokenCredential);

  void addToBlacklist(TokenId tokenId, Instant expiration);

  void removeRefreshToken(TokenId tokenId, Long userId);

  boolean isBlacklisted(TokenId tokenId);

  boolean isValidRefreshToken(TokenId userId, String refreshToken);
}
