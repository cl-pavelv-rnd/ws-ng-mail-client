package com.ws.ng.mail_client_runner.repository;

import com.ws.ng.mail_client_runner.config.MailClientRunnerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public class EmailRedisRepository {

    private static String redisKey;

    @Autowired
    private MailClientRunnerConfig config;

    @Autowired
    @Qualifier("last-message-indicator")
    private RedisTemplate<String, Long> redisTemplate;

    @PostConstruct
    public void init() {
        redisKey = getRedisKey(config.getUser());
    }

    public Long getLastMessageIndicator() {
        redisTemplate.boundValueOps(redisKey).setIfAbsent(0L);
        return redisTemplate.boundValueOps(redisKey).get();
    }

    public void updateLastMessageIndicator(Long lastMessageIndicator) {
        redisTemplate.boundValueOps(redisKey).set(lastMessageIndicator);
    }

    public void resetLastMessageIndicator(Long lastReceivedDate) {
        redisTemplate.boundValueOps(redisKey).set(lastReceivedDate);
    }

    private String getRedisKey(String email) {
        String[] keyParts = { config.getApp(), email, "last-message-indicator" };
        return String.join(".", keyParts);
    }

}
