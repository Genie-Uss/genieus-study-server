package shop.genieus.study.domains.auth.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shop.genieus.study.commons.provider.AuthProvider;
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
public class AuthorizationService implements AuthProvider {
  private final TokenRepository tokenRepository;
  private final TokenUtils tokenUtils;
  private final PrincipalCache principalCache;
  private final UserService userService;

  public TokenValidationResult validateAccessToken(String accessToken) {
    if (accessToken == null || accessToken.isBlank()) {
      throw new IllegalArgumentException("액세스 토큰은 필수입니다.");
    }

    return validateTokenAndCheckBlacklist(accessToken);
  }

  public CustomPrincipal getPrincipal(Long userId) {
    return getUserPrincipal(userId);
  }

  @Override
  public String getUserNickname(Long userId) {
    return getUserPrincipal(userId).nickname();
  }

  private CustomPrincipal getUserPrincipal(Long userId) {

    if (userId == null) {
      throw new IllegalArgumentException("사용자 ID는 필수입니다.");
    }

    // 캐시에서 Principal 조회
    CustomPrincipal principal = principalCache.findById(userId);

    // 캐시에 없으면 DB에서 조회 후 캐시에 저장
    if (principal == null) {
      try {
        UserInfo user = userService.findByUserId(userId);
        principal = new CustomPrincipal(user.id(), user.roleName(), user.nickname());
        principalCache.save(userId, principal);

        if (log.isDebugEnabled()) {
          log.debug("사용자 Principal 생성 및 캐싱 - 사용자: {}", userId);
        }
      } catch (Exception e) {
        log.warn("사용자 정보 조회 실패 - 사용자: {}, 원인: {}", userId, e.getMessage());
        throw new IllegalArgumentException("사용자 정보를 찾을 수 없습니다.");
      }
    }

    return principal;
  }

  private TokenValidationResult validateTokenAndCheckBlacklist(String token) {
    TokenValidationResult tokenValidationResult;
    try {
      tokenValidationResult = tokenUtils.validateTokenAndExtractId(token);
    } catch (TokenExpiredException e) {
      log.info("토큰 검증 실패 - 만료된 토큰");
      return TokenValidationResult.expiredAccessToken();
    } catch (Exception e) {
      log.warn("토큰 검증 실패 - 원인: {}", e.getMessage());
      throw new IllegalArgumentException(e.getMessage());
    }

    // 블랙리스트 확인
    if (tokenRepository.isBlacklisted(tokenValidationResult.getTokenId())) {
      log.warn("블랙리스트에 등록된 토큰 - 사용자: {}", tokenValidationResult.getUserId());
      throw new IllegalArgumentException("차단된 JWT 토큰입니다.");
    }

    return tokenValidationResult;
  }
}
