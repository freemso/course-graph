package edu.fudan;


import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableNeo4jRepositories("edu.fudan.repository")
@EntityScan(basePackages = "edu.fudan.domain")
@EnableTransactionManagement
public class Application {

    /**
     * Field name in request attributes that holds current user id
     */
    public static final String CURRENT_USER_ID = "CURRENT_USER_ID";

    /**
     * TTL for a authorization token in hour
     */
    public static final int TOKEN_EXPIRES_HOUR = 72;

    /**
     * Field name in request header that holds authorization token
     */
    public static final String AUTHORIZATION = "authorization";

    /**
     * Length of the id of the resources
     */
    public static final int ID_LENGTH = 7;

    /**
     * Regex for password validation
     *
     * Explanation:
     * ^                 # start-of-string
     * (?=.*[0-9])       # a digit must occur at least once
     * (?=.*[a-z])       # a lower case letter must occur at least once
     * (?=.*[A-Z])       # an upper case letter must occur at least once
     * (?=.*[@#$%^&+=])  # a special character must occur at least once
     * (?=\S+$)          # no whitespace allowed in the entire string
     * .{8,}             # anything, at least eight places though
     * $                 # end-of-string
     */
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    /**
     * Regex for email validation
     */
    public static final String EMAIL_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

    @Value("${spring.data.neo4j.uri}")
    private String neo4jUri;

    @Value("${spring.data.neo4j.username}")
    private String neo4jUsername;

    @Value("${spring.data.neo4j.password}")
    private String neo4jPassword;

    @Bean
    public org.neo4j.ogm.config.Configuration configuration() {
        return new org.neo4j.ogm.config.Configuration.Builder()
                .credentials(neo4jUsername, neo4jPassword)
                .uri(neo4jUri)
                .build();
    }

    @Bean
    public SessionFactory sessionFactory() {
        // with domain entity base package(s)
        return new SessionFactory(configuration(), "edu.fudan.domain");
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

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
