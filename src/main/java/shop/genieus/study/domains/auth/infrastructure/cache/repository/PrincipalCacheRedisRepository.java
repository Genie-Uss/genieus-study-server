package shop.genieus.study.domains.auth.infrastructure.cache.repository;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import shop.genieus.study.domains.auth.presentation.dto.CustomPrincipal;

@Repository
@RequiredArgsConstructor
public class PrincipalCacheRedisRepository {
  private static final String PRINCIPAL_PREFIX = "principal:user:";

  private final RedisTemplate<String, CustomPrincipal> principalRedisTemplate;

  public CustomPrincipal save(Long userId, CustomPrincipal principal, long ttlMillis) {
    if (userId == null || principal == null) {
      return null;
    }

    String key = PRINCIPAL_PREFIX + userId;

    try {
      principalRedisTemplate.opsForValue().set(key, principal, ttlMillis, TimeUnit.MILLISECONDS);
      return principal;
    } catch (Exception e) {
      return principal;
    }
  }

  public CustomPrincipal findById(Long userId) {
    if (userId == null) {
      return null;
    }

    String key = PRINCIPAL_PREFIX + userId;
    try {
      return principalRedisTemplate.opsForValue().get(key);
    } catch (Exception e) {
      return null;
    }
  }
}
