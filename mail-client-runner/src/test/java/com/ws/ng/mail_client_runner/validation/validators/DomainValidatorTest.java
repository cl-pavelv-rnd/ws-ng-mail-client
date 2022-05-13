package com.ws.ng.mail_client_runner.validation.validators;

import com.ws.ng.mail_client_connector.message.imap.IMAPMessage;
import com.ws.ng.mail_client_runner.config.MailClientRunnerConfig;
import org.apache.commons.mail.util.MimeMessageParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.UIDFolder;
import javax.mail.internet.InternetAddress;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {MailClientRunnerConfig.class, DomainValidator.class})
public class DomainValidatorTest {

    @InjectMocks
    @Autowired
    private DomainValidator domainValidator;

    @Autowired
    private MailClientRunnerConfig config;

    @Mock
    private Message message;

    @Mock
    private MimeMessageParser parser;

    @Mock(extraInterfaces = UIDFolder.class)
    private Folder folder;

    private IMAPMessage imapMessage;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        imapMessage = new IMAPMessage(message, parser);
    }

    @Test
    public void testValidate_validEmailDomain_noExceptionThrown() throws Exception {
        Set<String> allowedDomains = config.getAllowedDomains();
        assert allowedDomains.size() != 0;

        InternetAddress address = new InternetAddress();
        address.setAddress("wstu1@" + allowedDomains.iterator().next());

        InternetAddress[] addresses = new InternetAddress[1];
        addresses[0] = address;

        when(message.getFrom()).thenReturn(addresses);
        when(message.getFolder()).thenReturn(folder);
        when(((UIDFolder)folder).getUID(message)).thenReturn(1L);

        domainValidator.validate(imapMessage);
    }

    @Test
    public void testValidate_invalidEmailDomain_exceptionThrown() throws Exception {
        InternetAddress address = new InternetAddress();
        address.setAddress("wstu1@walla.com");

        InternetAddress[] addresses = new InternetAddress[1];
        addresses[0] = address;

        when(message.getFrom()).thenReturn(addresses);
        when(message.getFolder()).thenReturn(folder);
        when(((UIDFolder)folder).getUID(message)).thenReturn(1L);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            domainValidator.validate(imapMessage);
        });
        assertEquals("Email domain is not an allowed domain", thrown.getMessage());
    }

}
