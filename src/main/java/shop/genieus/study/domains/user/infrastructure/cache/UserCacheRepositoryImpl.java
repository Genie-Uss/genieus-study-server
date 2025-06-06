package shop.genieus.study.domains.user.infrastructure.cache;

import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import shop.genieus.study.commons.provider.model.UserInfo;
import shop.genieus.study.domains.user.application.repository.UserCacheRepository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserCacheRepositoryImpl implements UserCacheRepository {
  private static final String PARTICIPATING_USERS_KEY = "participating:users";
  private static final long CACHE_TTL_DAYS = 7;

  private final RedisTemplate<String, Object> genericRedisTemplate;

  @Override
  public List<UserInfo> findAllParticipatingUsers() {
    List<UserInfo> result = null;
    try {
      List<UserInfo> cachedUsers =
          (List<UserInfo>) genericRedisTemplate.opsForValue().get(PARTICIPATING_USERS_KEY);

      if (cachedUsers != null && !cachedUsers.isEmpty()) {
        log.debug("참여 사용자 캐시 히트: {} users", cachedUsers.size());
        result = cachedUsers;
      } else {
        log.debug("참여 사용자 캐시 미스");
      }

    } catch (Exception e) {
      log.warn("캐시 조회 중 오류 발생: {}", e.getMessage());
    }
    return result;
  }

  @Override
  public void saveParticipatingUsers(List<UserInfo> users) {
    try {
      genericRedisTemplate
          .opsForValue()
          .set(PARTICIPATING_USERS_KEY, users, CACHE_TTL_DAYS, TimeUnit.DAYS);
      log.debug("참여 사용자 정보 캐싱 완료: {} users", users.size());
    } catch (Exception e) {
      log.warn("캐싱 중 오류 발생: {}", e.getMessage());
    }
  }

  @Override
  public void invalidateParticipatingUsers() {
    try {
      genericRedisTemplate.delete(PARTICIPATING_USERS_KEY);
      log.info("참여 사용자 캐시 무효화 완료");
    } catch (Exception e) {
      log.warn("캐시 무효화 중 오류: {}", e.getMessage());
    }
  }
}
