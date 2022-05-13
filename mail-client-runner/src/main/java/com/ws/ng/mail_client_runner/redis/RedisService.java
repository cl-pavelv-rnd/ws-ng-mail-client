package com.ws.ng.mail_client_runner.redis;

import com.ws.ng.mail_client_runner.repository.EmailRedisRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This service is responsible for interacting with the Redis cache,
 * it is used to manage the last processed email.
 */
@Service
@Log4j2
public class RedisService {

    @Autowired
    private EmailRedisRepository repository;

    public Long getLastMessageIndicator() {
        Long lastMessageIndicator = repository.getLastMessageIndicator();
        log.info("Getting email's last message indicator {}", lastMessageIndicator);
        return lastMessageIndicator;
    }

    public void updateLastMessageIndicator(Long lastMessageIndicator) {
        log.info("Setting email's last message indicator {}", lastMessageIndicator);
        repository.updateLastMessageIndicator(lastMessageIndicator);
    }

    public void resetLastMessageIndicator(Long lastMessageIndicator) {
        log.info("Resetting email's last message indicator {}", lastMessageIndicator);
        repository.resetLastMessageIndicator(lastMessageIndicator);
    }

}
