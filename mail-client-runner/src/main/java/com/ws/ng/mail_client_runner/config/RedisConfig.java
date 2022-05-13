package com.ws.ng.mail_client_runner.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

@Configuration
public class RedisConfig {

    @Autowired
    private Environment env;

    @Bean
    @Qualifier("last-message-indicator")
    public RedisTemplate<String, Long> redisTemplate() {
        var template = new RedisTemplate<String, Long>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
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
