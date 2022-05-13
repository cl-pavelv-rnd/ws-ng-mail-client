package com.ws.ng.mail_client_runner.service;

import com.r39.ws.cns.datamodel.icd.userDirectory.IcdUser;
import com.ws.ng.mail_client_avro.ExternalEmailMessageEvent;
import com.ws.ng.mail_client_connector.message.AbstractMessage;
import com.ws.ng.mail_client_connector.transformer.EmailMessageTransformer;
import com.ws.ng.mail_client_datamodel.email.IcdEmailMessage;
import com.ws.ng.mail_client_runner.config.MailClientRunnerConfig;
import com.ws.ng.mail_client_runner.runner.UsersCacheRunner;
import com.ws.ng.mail_client_runner.validation.EmailValidationExecutor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.ws.ng.mail_client_datamodel.header.WsUserHeaders.*;

/**
 * This service is responsible for interacting with the Email Server,
 * perform validations and send the messages to a Kafka topic.
 */
@Service
@Log4j2
public class EmailService {

    @Autowired
    private MailClientRunnerConfig config;

    @Autowired
    private EmailMessageTransformer transformer;

    @Autowired
    private EmailValidationExecutor validationRunner;

    @Autowired
    private UsersCacheRunner usersCache;

    @Autowired
    @Qualifier("kafkaTemplate_ExternalEmailMessageEvent")
    private KafkaTemplate<String, ExternalEmailMessageEvent> kafkaProducer;

    /**
     * Processing includes:<BR>
     *     1. General validations<BR>
     *     2. Fire an event to Kafka with message details<BR>
     */
    public void processMessages(AbstractMessage[] messages) throws Exception {
        log.info("Received processMessages() with {} messages", messages.length);

        List<AbstractMessage> validatedEmailMessages = validationRunner.getValidatedEmailMessages(messages);

        if (log.isDebugEnabled()) {
            List<IcdEmailMessage> icdEmailMessages = validatedEmailMessages
                    .stream()
                    .map(message -> transformer.toIcd(message))
                    .collect(Collectors.toList());
            log.debug("Validated email messages: " + icdEmailMessages);
        }

        for (AbstractMessage message : validatedEmailMessages) {
            ExternalEmailMessageEvent avroEmailMessageEvent = transformer.toAvro(message);

            String requestingUser = avroEmailMessageEvent.getFrom().getUsername();
            IcdUser icdUser = usersCache.get(requestingUser);
            if (icdUser == null) {
                throw new RuntimeException("Sending user is not a WS user");
            }

            var record = new ProducerRecord<String, ExternalEmailMessageEvent>(config.getOutputTopic(), null, avroEmailMessageEvent);
            record.headers().add(USER_NAME, icdUser.getUserName().getBytes());
            record.headers().add(CLASSIFICATION_VALUE, String.valueOf(icdUser.getClassificationValue()).getBytes());
            record.headers().add(POSITION_ID, icdUser.getOrgPositionId().getBytes());
            record.headers().add(DIVISION_ID, icdUser.getDivisionId().getBytes());
            record.headers().add(ROLES, String.join(",", icdUser.getRoles()).getBytes());

            kafkaProducer.send(record);

            message.markAsSeen();
        }
    }

}
