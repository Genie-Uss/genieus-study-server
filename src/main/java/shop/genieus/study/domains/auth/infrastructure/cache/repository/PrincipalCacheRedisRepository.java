package shop.genieus.study.domains.auth.infrastructure.cache.repository;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import shop.genieus.study.domains.auth.domain.vo.TokenType;
import shop.genieus.study.domains.auth.presentation.dto.CustomPrincipal;

@Repository
@RequiredArgsConstructor
public class PrincipalCacheRedisRepository {
  private static final String PRINCIPAL_PREFIX = "principal:user:";

  private final RedisTemplate<String, CustomPrincipal> principalRedisTemplate;

  public CustomPrincipal save(Long userId, CustomPrincipal principal) {
    String key = PRINCIPAL_PREFIX + userId;
    long principalTtl = TokenType.ACCESS.getExpiration().toMillis();

    principalRedisTemplate.opsForValue().set(key, principal, principalTtl, TimeUnit.MILLISECONDS);
    return principal;
  }

  public CustomPrincipal findById(Long userId) {
    String key = PRINCIPAL_PREFIX + userId;
    return principalRedisTemplate.opsForValue().get(key);
  }
}
