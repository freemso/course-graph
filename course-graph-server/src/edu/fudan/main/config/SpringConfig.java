package edu.fudan.main.config;

import edu.fudan.main.repository.UserRepository;
import edu.fudan.main.rest.AuthorizationInterceptor;
import edu.fudan.main.rest.CurrentUserMethodArgumentResolver;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableNeo4jRepositories("edu.fudan.main.repository")
@EntityScan(basePackages = "edu.fudan.main.domain")
@EnableTransactionManagement
public class SpringConfig implements WebMvcConfigurer{

    private static final String NEO4J_USERNAME = "neo4j";

    private static final String NEO4J_PASSWORD = "123456";

    private static final String NEO4J_URL = "http://localhost:7474";


    @Bean
    public org.neo4j.ogm.config.Configuration configuration() {
        return new org.neo4j.ogm.config.Configuration.Builder()
                .uri(NEO4J_URL)
                .credentials(NEO4J_USERNAME, NEO4J_PASSWORD)
                .build();
    }

    @Bean
    public SessionFactory sessionFactory() {
        // with domain entity base package(s)
        return new SessionFactory(configuration(), "edu.fudan.main.domain");
    }

    @Bean
    public Neo4jTransactionManager transactionManager() {
        return new Neo4jTransactionManager(sessionFactory());
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    RedisTemplate<Long, String> redisTemplate() {
        RedisTemplate<Long, String> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new JdkSerializationRedisSerializer());
        return template;
    }

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorizationInterceptor authorizationInterceptor;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new CurrentUserMethodArgumentResolver(userRepository));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(authorizationInterceptor);
    }
}