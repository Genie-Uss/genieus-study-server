package shop.genieus.study.domains.auth.application;

import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shop.genieus.study.commons.provider.UserProvider;
import shop.genieus.study.commons.provider.dto.UserInfo;
import shop.genieus.study.domains.auth.application.cache.PrincipalCache;
import shop.genieus.study.domains.auth.application.dto.info.CredentialInfo;
import shop.genieus.study.domains.auth.application.dto.result.ReIssueTokenResult;
import shop.genieus.study.domains.auth.application.dto.result.TokenValidationResult;
import shop.genieus.study.domains.auth.application.repository.TokenRepository;
import shop.genieus.study.domains.auth.application.util.IdGenerator;
import shop.genieus.study.domains.auth.application.util.TokenUtils;
import shop.genieus.study.domains.auth.domain.exception.CustomAuthenticationException;
import shop.genieus.study.domains.auth.domain.exception.TokenExpiredException;
import shop.genieus.study.domains.auth.domain.vo.TokenCredential;
import shop.genieus.study.domains.auth.domain.vo.TokenId;
import shop.genieus.study.domains.auth.domain.vo.TokenPair;
import shop.genieus.study.domains.auth.presentation.dto.CustomPrincipal;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final TokenRepository tokenRepository;
  private final PrincipalCache principalCache;
  private final TokenUtils tokenUtils;
  private final IdGenerator idGenerator;
  private final UserProvider userProvider;

  public CustomPrincipal getUserByCredentialInfo(CredentialInfo info) {
    try {
      UserInfo user = userProvider.validateUserCredentials(info.email(), info.password());
      CustomPrincipal principal = new CustomPrincipal(user.id(), user.roleName(), user.nickname());
      principalCache.save(user.id(), principal);

      return principal;
    } catch (Exception e) {
      throw CustomAuthenticationException.create(e.getMessage());
    }
  }

  public TokenPair createAuthenticationToken(Long id) {
    String tokenIdValue = idGenerator.generateUniqueId();

    return tokenUtils.createTokenPair(tokenIdValue, id);
  }

  public void saveRefreshToken(TokenId tokenId, Long userId, TokenCredential refreshToken) {
    tokenRepository.saveRefreshToken(tokenId, userId, refreshToken);
  }

  public ReIssueTokenResult reIssueTokenPair(String accessToken, String refreshToken) {
    TokenValidationResult validationResult;
    try {
      validationResult = tokenUtils.validateTokenAndExtractId(refreshToken);
    } catch (TokenExpiredException e) {
      return ReIssueTokenResult.expiredRefreshToken();
    }

    Long userId = validationResult.getUserId();
    TokenId oldTokenId = validationResult.getTokenId();

    validateRefreshToken(oldTokenId, refreshToken);
    revokeTokenPair(accessToken, oldTokenId, userId);

    TokenPair newTokenPair = generateAndPersistTokenPair(userId);
    log.info("토큰 갱신 성공- Id: {}", userId);

    return ReIssueTokenResult.of(newTokenPair, userId);
  }

  private void validateRefreshToken(TokenId tokenId, String refreshToken) {
    boolean isValidRefreshToken = tokenRepository.isValidRefreshToken(tokenId, refreshToken);
    if (!isValidRefreshToken) {
      throw new IllegalArgumentException("저장된 리프레시 토큰과 일치하지 않습니다. 다시 로그인해주세요.");
    }
  }

  private void revokeTokenPair(String accessToken, TokenId tokenId, Long userId) {
    Instant expirationTime = tokenUtils.getExpirationTime(accessToken);
    if (expirationTime.isAfter(Instant.now())) {
      tokenRepository.addToBlacklist(tokenId, expirationTime);
    }
    tokenRepository.removeRefreshToken(tokenId, userId);
  }

  private TokenPair generateAndPersistTokenPair(Long userId) {
    String tokenId = idGenerator.generateUniqueId();
    TokenPair tokenPair = tokenUtils.createTokenPair(tokenId, userId);
    tokenRepository.saveRefreshToken(
        tokenPair.getTokenId(), userId, tokenPair.getRefreshTokenCredential());
    return tokenPair;
  }
}
