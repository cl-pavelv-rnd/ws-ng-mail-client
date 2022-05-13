package com.ws.ng.mail_client_listener.config;

import com.ws.ng.mail_client_datamodel.email.EmailEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

@Configuration
public class RedisConfig {

    private static String redisKey;

    @Autowired
    private MailClientListenerConfig config;

    @Autowired
    private Environment env;

    @PostConstruct
    public void init() {
        redisKey = config.getApp() + ".events";
    }

    @Bean
    public BoundListOperations<String, EmailEvent> redisRequestListOperations() {
        var template = new RedisTemplate<String, EmailEvent>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(EmailEvent.class));
        template.afterPropertiesSet();
        return template.boundListOps(redisKey);
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        var standaloneConfig = new RedisStandaloneConfiguration();
        String redisHost = env.getProperty("spring.redis.host");
        if (!StringUtils.isEmpty(redisHost)) {
            standaloneConfig.setHostName(redisHost);
        }
        String redisPortStr = env.getProperty("spring.redis.port");
        if (!StringUtils.isEmpty(redisPortStr)) {
            standaloneConfig.setPort(Integer.parseInt(redisPortStr));
        }
        String redisPassword = env.getProperty("spring.redis.password");
        if (!StringUtils.isEmpty(redisPassword)) {
            standaloneConfig.setPassword(redisPassword);
        }
        return new JedisConnectionFactory(standaloneConfig);
    }

}
