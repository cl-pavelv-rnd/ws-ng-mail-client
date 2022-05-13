package com.ws.ng.mail_client_listener.service;

import com.ws.ng.mail_client_avro.ExternalEmailMessageEvent;
import com.ws.ng.mail_client_datamodel.email.EmailEvent;
import com.ws.ng.mail_client_datamodel.email.IcdEmailMessage;
import com.ws.ng.mail_client_datamodel.header.WsUserHeaders;
import com.ws.ng.mail_client_listener.repository.EmailEventRepository;
import com.ws.ng.mail_client_listener.transformer.avro.AvroMessageTransformer;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class RedisService {

    @Autowired
    private AvroMessageTransformer transformer;

    @Autowired
    private EmailEventRepository repository;

    public void push(ConsumerRecord<String, ExternalEmailMessageEvent> event) {
        WsUserHeaders headers = transformer.toHeaders(event.headers());
        IcdEmailMessage emailMessage = transformer.toIcd(event.value());

        EmailEvent emailEvent = EmailEvent
                .builder()
                .headers(headers)
                .emailMessage(emailMessage)
                .build();

        repository.push(emailEvent);

        log.info("Event with id {} was pushed to Redis", emailMessage.getId());
    }

    public EmailEvent pop() {
        EmailEvent event = repository.pop();

        if (event != null) {
            log.info("Event with id {} was retrieved from Redis", event.getEmailMessage().getId());
        } else {
            log.info("No events in Redis after timeout...");
        }

        return event;
    }

}
