package shop.genieus.study.domains.auth.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shop.genieus.study.commons.provider.dto.UserInfo;
import shop.genieus.study.domains.auth.application.cache.PrincipalCache;
import shop.genieus.study.domains.auth.application.dto.result.TokenValidationResult;
import shop.genieus.study.domains.auth.application.repository.TokenRepository;
import shop.genieus.study.domains.auth.application.util.TokenUtils;
import shop.genieus.study.domains.auth.domain.exception.TokenExpiredException;
import shop.genieus.study.domains.auth.presentation.dto.CustomPrincipal;
import shop.genieus.study.domains.user.application.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizationService {
  private final TokenRepository tokenRepository;
  private final TokenUtils tokenUtils;
  private final PrincipalCache principalCache;
  private final UserService userService;

  public TokenValidationResult validateAccessToken(String accessToken) {
    return validateTokenAndCheckBlacklist(accessToken);
  }

  public CustomPrincipal getPrincipal(Long userId) {
    CustomPrincipal principal = principalCache.findById(userId);
    if (principal == null) {
      UserInfo user = userService.findByUserId(userId);
      principal = new CustomPrincipal(user.id(), user.roleName(), user.nickname());
      principalCache.save(userId, principal);
    }
    return principal;
  }

  private TokenValidationResult validateTokenAndCheckBlacklist(String token) {
    TokenValidationResult tokenValidationResult;
    try {
      tokenValidationResult = tokenUtils.validateTokenAndExtractId(token);
    } catch (TokenExpiredException e) {
      return TokenValidationResult.expiredAccessToken();
    }
    if (tokenRepository.isBlacklisted(tokenValidationResult.getTokenId())) {
      throw new IllegalArgumentException("차단된 JWT 토큰입니다.");
    }
    return tokenValidationResult;
  }
}
