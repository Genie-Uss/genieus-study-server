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

    // 키 직렬화에 문자열 직렬화 사용
    template.setKeySerializer(new StringRedisSerializer());

    // 값 직렬화에 Jackson 직렬화 사용
    template.setValueSerializer(new Jackson2JsonRedisSerializer<>(CustomPrincipal.class));

    // 해시 키/값 직렬화 설정
    template.setHashKeySerializer(new StringRedisSerializer());
    template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(CustomPrincipal.class));

    // 템플릿 속성 사용 활성화
    template.setEnableTransactionSupport(false); // 트랜잭션 비활성화 - 성능 향상
    template.afterPropertiesSet();
    return template;
  }
}
