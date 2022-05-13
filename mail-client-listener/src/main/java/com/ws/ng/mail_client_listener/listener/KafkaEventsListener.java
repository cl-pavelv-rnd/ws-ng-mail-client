package com.ws.ng.mail_client_listener.listener;

import com.ws.ng.mail_client_avro.ExternalEmailMessageEvent;
import com.ws.ng.mail_client_listener.config.MailClientListenerConfig;
import com.ws.ng.mail_client_listener.service.RedisService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@EnableKafka
@Component
@Log4j2
public class KafkaEventsListener {

    @Autowired
    private MailClientListenerConfig config;

    @Autowired
    private RedisService service;

    @Bean
    public String topicName() {
        return config.getInputTopic();
    }

    @KafkaListener(topics = "#{@topicName}")
    public void listen(ConsumerRecord<String, ExternalEmailMessageEvent> event) {
        log.info("Received email message event: {}", ToStringBuilder.reflectionToString(event.value()));

        service.push(event);
    }

}
