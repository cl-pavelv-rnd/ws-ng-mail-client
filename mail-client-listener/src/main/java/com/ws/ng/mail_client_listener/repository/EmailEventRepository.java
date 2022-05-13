package com.ws.ng.mail_client_listener.repository;

import com.ws.ng.mail_client_datamodel.email.EmailEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@Log4j2
public class EmailEventRepository {

    @Autowired
    private BoundListOperations<String, EmailEvent> redisEventsQueue;

    public void push(EmailEvent event) {
        try {
            redisEventsQueue.rightPush(event);
        } catch (Exception e) {
            log.error("Failed while pushing message to Redis queue", e);
            throw e;
        }
    }

    public EmailEvent pop() {
        try {
            return redisEventsQueue.leftPop(1, TimeUnit.HOURS);
        } catch (Exception e) {
            log.error("Failed while popping message from Redis queue", e);
            throw e;
        }
    }

}
