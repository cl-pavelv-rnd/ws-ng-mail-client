package com.ws.ng.mail_client_runner.runner;

import com.ws.ng.mail_client_connector.connector.ClientProperties;
import com.ws.ng.mail_client_connector.connector.EmailServerConnector;
import com.ws.ng.mail_client_connector.message.AbstractMessage;
import com.ws.ng.mail_client_runner.config.MailClientRunnerConfig;
import com.ws.ng.mail_client_runner.redis.RedisService;
import com.ws.ng.mail_client_runner.service.EmailService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This runner is responsible for orchestrating the pulling mechanism against the Mail Server.
 */
@Component
@Log4j2
public class MailClientRunner implements Runnable {

    @Autowired
    private MailClientRunnerConfig config;

    @Autowired
    private ClientProperties clientProperties;

    @Autowired
    private EmailServerConnector emailConnector;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RedisService redisService;

    @PostConstruct
    public void init() throws Exception {
        log.info("MailClientRunner started...");

        emailConnector.init(clientProperties);
        emailConnector.connect();

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(this, 30, config.getEmailsPullDelay(), TimeUnit.SECONDS);
    }

    @SneakyThrows
    @Override
    public void run() {
        log.info("Checking for new messages...");

        try {
            Long from = redisService.getLastMessageIndicator() + 1;

            AbstractMessage[] abstractMessages = emailConnector.searchEmails(from);
            if (abstractMessages.length == 0) {
                log.info("No new messages at this time, sleeping for {} seconds", config.getEmailsPullDelay());
                return;
            }

            processMessages(abstractMessages);

            redisService.updateLastMessageIndicator(calcMaxMessageIndicator(abstractMessages));
        } catch (Exception e) {
            log.error("Error in MailClientRunner", e);
            System.exit(1);
        } finally {
            emailConnector.close();
        }
    }

    /**
     * Process new messages
     */
    private void processMessages(AbstractMessage[] messages) throws Exception {
        long startTime = System.currentTimeMillis();

        emailService.processMessages(messages);

        long endTime = System.currentTimeMillis();
        log.info("{} Messages were processed in {} ms", messages.length, (endTime - startTime));
    }

    private Long calcMaxMessageIndicator(AbstractMessage[] messages) {
        return Arrays.stream(messages)
                .map(AbstractMessage::getComparableValue)
                .max(Long::compareTo)
                .orElseThrow(() -> new RuntimeException("No value to sort by!"));

    }

}
