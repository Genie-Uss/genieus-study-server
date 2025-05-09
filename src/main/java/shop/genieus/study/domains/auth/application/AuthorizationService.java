package shop.genieus.study.domains.auth.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shop.genieus.study.domains.auth.application.cache.PrincipalCache;
import shop.genieus.study.domains.auth.application.dto.result.TokenValidationResult;
import shop.genieus.study.domains.auth.application.repository.TokenRepository;
import shop.genieus.study.domains.auth.application.util.TokenUtils;
import shop.genieus.study.domains.auth.presentation.annotation.CustomPrincipal;
import shop.genieus.study.domains.user.application.UserService;
import shop.genieus.study.domains.user.domain.entity.User;

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
      User user = userService.findById(userId);
      principal =
          new CustomPrincipal(user.getId(), user.getRole().name(), user.getNickname().getValue());
      principalCache.save(userId, principal);
    }
    return principal;
  }

  private TokenValidationResult validateTokenAndCheckBlacklist(String token) {
    TokenValidationResult tokenValidationResult = tokenUtils.validateTokenAndExtractId(token);
    if (tokenRepository.isBlacklisted(tokenValidationResult.getTokenId())) {
      throw new IllegalArgumentException("차단된 JWT 토큰입니다.");
    }
    return tokenValidationResult;
  }
}
