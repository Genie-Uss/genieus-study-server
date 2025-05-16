package shop.genieus.study.domains.auth.infrastructure.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import shop.genieus.study.domains.auth.application.cache.PrincipalCache;
import shop.genieus.study.domains.auth.domain.vo.TokenType;
import shop.genieus.study.domains.auth.infrastructure.cache.repository.PrincipalCacheRedisRepository;
import shop.genieus.study.domains.auth.presentation.dto.CustomPrincipal;

@Slf4j
@Component
@RequiredArgsConstructor
public class PrincipalCacheImpl implements PrincipalCache {
  private final PrincipalCacheRedisRepository redisRepository;

  @Override
  public CustomPrincipal findById(Long userId) {
    if (userId == null) {
      return null;
    }

    CustomPrincipal principal = redisRepository.findById(userId);

    if (log.isDebugEnabled() && principal != null) {
      log.debug("캐시에서 Principal 조회 성공: userId={}", userId);
    }

    return principal;
  }

  @Override
  public CustomPrincipal save(Long userId, CustomPrincipal principal) {
    if (userId == null || principal == null) {
      return null;
    }

    // 액세스 토큰보다 약간 더 길게 캐시 유지
    long extendedTtl = TokenType.ACCESS.getExpiration().toMillis() + 30_000;
    CustomPrincipal saved = redisRepository.save(userId, principal, extendedTtl);

    if (log.isDebugEnabled()) {
      log.debug("Principal 캐시 저장: userId={}, ttl={}ms", userId, extendedTtl);
    }

    return saved;
  }
}
