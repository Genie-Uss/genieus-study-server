package shop.genieus.study.commons.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import shop.genieus.study.domains.auth.presentation.dto.CustomPrincipal;

@Configuration
public class RedisConfig {

  @Bean
  public RedisTemplate<String, CustomPrincipal> principalRedisTemplate(
    RedisConnectionFactory connectionFactory) {
      RedisTemplate<String, CustomPrincipal> template = new RedisTemplate<>();
      template.setConnectionFactory(connectionFactory);

      template.setKeySerializer(new StringRedisSerializer());

      template.setValueSerializer(new Jackson2JsonRedisSerializer<>(CustomPrincipal.class));

      template.setHashKeySerializer(new StringRedisSerializer());
      template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(CustomPrincipal.class));

      template.afterPropertiesSet();
      return template;
  }
}
