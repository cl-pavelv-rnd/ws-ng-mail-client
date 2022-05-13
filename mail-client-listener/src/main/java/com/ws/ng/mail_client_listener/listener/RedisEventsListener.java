package com.ws.ng.mail_client_listener.listener;

import com.ws.ng.mail_client_datamodel.email.EmailEvent;
import com.ws.ng.mail_client_listener.service.EventsService;
import com.ws.ng.mail_client_listener.service.RedisService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@Log4j2
public class RedisEventsListener implements Runnable {

    @Autowired
    private RedisService redisService;

    @Autowired
    private EventsService eventsService;

    @PostConstruct
    public void init() {
        log.info("RedisEventsListener started...");

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.schedule(this, 0, TimeUnit.SECONDS);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while (true) {
            try {
                EmailEvent emailEvent = redisService.pop();

                // pop by timeout
                if (emailEvent == null) {
                    continue;
                }

                eventsService.subscribe(emailEvent);
            } catch (Exception e) {
                log.error("Error in message handling", e);
            }
        }
    }

}
