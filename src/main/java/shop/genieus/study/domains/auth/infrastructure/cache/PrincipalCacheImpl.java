package shop.genieus.study.domains.auth.infrastructure.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.genieus.study.domains.auth.application.cache.PrincipalCache;
import shop.genieus.study.domains.auth.infrastructure.cache.repository.PrincipalCacheRedisRepository;
import shop.genieus.study.domains.auth.presentation.dto.CustomPrincipal;

@Component
@RequiredArgsConstructor
public class PrincipalCacheImpl implements PrincipalCache {
  private final PrincipalCacheRedisRepository redisRepository;

  @Override
  public CustomPrincipal findById(Long userId) {
    return redisRepository.findById(userId);
  }

  @Override
  public CustomPrincipal save(Long userId, CustomPrincipal principal) {
    return redisRepository.save(userId, principal);
  }
}
