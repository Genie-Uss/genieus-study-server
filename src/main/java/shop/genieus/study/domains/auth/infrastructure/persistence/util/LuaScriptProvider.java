package shop.genieus.study.domains.auth.infrastructure.persistence.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

public class LuaScriptProvider {

  private static final RedisScript<Long> SAVE_REFRESH_TOKEN_SCRIPT;

  static {
    DefaultRedisScript<Long> refreshTokenScript = new DefaultRedisScript<>();
    refreshTokenScript.setScriptSource(
        new ResourceScriptSource(new ClassPathResource("redis/save-refresh-token.lua")));
    refreshTokenScript.setResultType(Long.class);
    SAVE_REFRESH_TOKEN_SCRIPT = refreshTokenScript;
  }

  public static RedisScript<Long> getSaveRefreshTokenScript() {
    return SAVE_REFRESH_TOKEN_SCRIPT;
  }
}
