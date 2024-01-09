package com.greenatom.clientselfservice.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {

    private final String REDIS_HOSTNAME;
    private final String REDIS_PASSWORD;

    private final String REDIS_USERNAME;

    @Autowired
    public RedisConfiguration(@Value("${redis_host_name}") String redisHostname, @Value("${redis_password}") String redisPassword,@Value("${redis_username}") String redisUsername) {
        this.REDIS_HOSTNAME = redisHostname;
        REDIS_PASSWORD = redisPassword;
        REDIS_USERNAME = redisUsername;
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("5.35.83.19");
        configuration.setPort(6379);
        configuration.setPassword(REDIS_PASSWORD);
        configuration.setUsername(REDIS_USERNAME);
        return new JedisConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new JdkSerializationRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        template.setEnableTransactionSupport(true);
        template.afterPropertiesSet();
        return template;
    }
}
