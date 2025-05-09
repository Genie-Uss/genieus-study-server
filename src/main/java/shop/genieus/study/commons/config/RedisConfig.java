package shop.genieus.study.commons.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import shop.genieus.study.domains.auth.presentation.dto.CustomPrincipal;

@Configuration
public class RedisConfig {

  @Bean
  public RedisTemplate<String, CustomPrincipal> principalRedisTemplate(
      RedisConnectionFactory redisConnectionFactory, ObjectMapper objectMapper) {
    RedisTemplate<String, CustomPrincipal> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);

    StringRedisSerializer stringSerializer = new StringRedisSerializer();
    GenericJackson2JsonRedisSerializer valueSerializer =
        new GenericJackson2JsonRedisSerializer(objectMapper);

    template.setKeySerializer(stringSerializer);
    template.setValueSerializer(valueSerializer);
    template.setHashKeySerializer(stringSerializer);
    template.setHashValueSerializer(valueSerializer);

    template.afterPropertiesSet();
    return template;
  }
}
