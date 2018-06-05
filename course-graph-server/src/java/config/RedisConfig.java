package java.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Autowired
    @Bean
    RedisTemplate<Long, String> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        final RedisTemplate<Long, String> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory);
        template.setKeySerializer(new JdkSerializationRedisSerializer());
        return template;
    }
}