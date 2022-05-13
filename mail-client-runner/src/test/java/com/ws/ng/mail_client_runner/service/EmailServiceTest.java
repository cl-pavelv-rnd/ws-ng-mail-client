package com.ws.ng.mail_client_runner.service;

import com.r39.ws.cns.datamodel.icd.userDirectory.IcdUser;
import com.ws.ng.mail_client_avro.EmailAddress;
import com.ws.ng.mail_client_avro.ExternalEmailMessageEvent;
import com.ws.ng.mail_client_connector.message.AbstractMessage;
import com.ws.ng.mail_client_connector.message.imap.IMAPMessage;
import com.ws.ng.mail_client_connector.transformer.EmailMessageTransformer;
import com.ws.ng.mail_client_runner.TestsConfig;
import com.ws.ng.mail_client_runner.runner.UsersCacheRunner;
import com.ws.ng.mail_client_runner.validation.EmailValidationExecutor;
import org.apache.commons.mail.util.MimeMessageParser;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import javax.mail.Message;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {TestsConfig.class})
public class EmailServiceTest {

    @InjectMocks
    @Autowired
    private EmailService service;

    @Mock
    private EmailMessageTransformer transformer;

    @Mock
    private EmailValidationExecutor validationRunner;

    @Mock
    private UsersCacheRunner usersCache;

    @Mock
    private KafkaTemplate<String, ExternalEmailMessageEvent> kafkaProducer;

    @Mock
    private Message message;

    @Mock
    private MimeMessageParser parser;

    private IMAPMessage imapMessage;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        imapMessage = new IMAPMessage(message, parser);
    }

    @Test
    public void testProcessMessages_validFlow_allMocksAreCalled() throws Exception {
        EmailAddress emailAddress = new EmailAddress();
        emailAddress.setUsername("wstu1");

        ExternalEmailMessageEvent event = new ExternalEmailMessageEvent();
        event.setFrom(emailAddress);

        IcdUser icdUser = new IcdUser();
        icdUser.setUserName("wstu1");
        icdUser.setClassificationValue(100);
        icdUser.setOrgPositionId("POSITION_ID");
        icdUser.setDivisionId("DIVISION_ID");
        icdUser.setRoles(Collections.singletonList("SUPER-USER"));

        AbstractMessage[] abstractMessages = new IMAPMessage[]{imapMessage};

        when(validationRunner.getValidatedEmailMessages(abstractMessages)).thenReturn(Collections.singletonList(imapMessage));
        when(transformer.toAvro(imapMessage)).thenReturn(event);
        when(usersCache.get("wstu1")).thenReturn(icdUser);

        service.processMessages(abstractMessages);

        verify(validationRunner, times(1)).getValidatedEmailMessages(abstractMessages);
        verify(transformer, times(1)).toAvro(imapMessage);
        verify(usersCache, times(1)).get("wstu1");
        verify(kafkaProducer, times(1)).send(ArgumentMatchers.<ProducerRecord<String, ExternalEmailMessageEvent>>any());
    }

    @Test
    public void testProcessMessages_noValidMessage_flowSkipped() throws Exception {
        AbstractMessage[] abstractMessages = new IMAPMessage[]{imapMessage};

        when(validationRunner.getValidatedEmailMessages(abstractMessages)).thenReturn(new ArrayList<>());

        service.processMessages(abstractMessages);

        verify(validationRunner, times(1)).getValidatedEmailMessages(abstractMessages);
        verify(transformer, times(0)).toAvro(imapMessage);
        verify(usersCache, times(0)).get("wstu1");
        verify(kafkaProducer, times(0)).send(ArgumentMatchers.<ProducerRecord<String, ExternalEmailMessageEvent>>any());
    }

    @Test
    public void testProcessMessages_noValidUser_flowSkipped() throws Exception {
        EmailAddress emailAddress = new EmailAddress();
        emailAddress.setUsername("wstu1");

        ExternalEmailMessageEvent event = new ExternalEmailMessageEvent();
        event.setFrom(emailAddress);

        IcdUser icdUser = new IcdUser();
        icdUser.setUserName("wstu1");
        icdUser.setClassificationValue(100);
        icdUser.setOrgPositionId("POSITION_ID");
        icdUser.setDivisionId("DIVISION_ID");
        icdUser.setRoles(Collections.singletonList("SUPER-USER"));

        AbstractMessage[] abstractMessages = new IMAPMessage[]{imapMessage};

        when(validationRunner.getValidatedEmailMessages(abstractMessages)).thenReturn(Collections.singletonList(imapMessage));
        when(transformer.toAvro(imapMessage)).thenReturn(event);
        when(usersCache.get("wstu1")).thenReturn(null);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            service.processMessages(abstractMessages);
        });
        assertEquals("Sending user is not a WS user", thrown.getMessage());

        verify(validationRunner, times(1)).getValidatedEmailMessages(abstractMessages);
        verify(transformer, times(1)).toAvro(imapMessage);
        verify(usersCache, times(1)).get("wstu1");
        verify(kafkaProducer, times(0)).send(ArgumentMatchers.<ProducerRecord<String, ExternalEmailMessageEvent>>any());
    }

}
