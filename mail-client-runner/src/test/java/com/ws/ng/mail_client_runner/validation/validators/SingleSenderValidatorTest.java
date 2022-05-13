package com.ws.ng.mail_client_runner.validation.validators;

import com.ws.ng.mail_client_connector.message.imap.IMAPMessage;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.mail.util.MimeMessageParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.UIDFolder;
import javax.mail.internet.InternetAddress;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class SingleSenderValidatorTest {

    @InjectMocks
    @Autowired
    private SingleSenderValidator singleSenderValidator;

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
    public void testValidate_singleSender_noExceptionThrown() throws Exception {
        InternetAddress[] sender = createInternetAddress("wstu1@rnd-hub.com");

        when(message.getFrom()).thenReturn(sender);
        when(message.getFolder()).thenReturn(folder);
        when(((UIDFolder)folder).getUID(message)).thenReturn(1L);

        singleSenderValidator.validate(imapMessage);
    }

    @Test
    public void testValidate_noSender_exceptionThrown() throws Exception {
        when(message.getFrom()).thenReturn(null);
        when(message.getFolder()).thenReturn(folder);
        when(((UIDFolder)folder).getUID(message)).thenReturn(1L);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            singleSenderValidator.validate(imapMessage);
        });
        assertEquals("No single sender was found", thrown.getMessage());
    }

    @Test
    public void testValidate_multipleSenders_exceptionThrown() throws Exception {
        InternetAddress[] sender1 = createInternetAddress("wstu1@rnd-hub.com");
        InternetAddress[] sender2 = createInternetAddress("wstu2@rnd-hub.com");

        InternetAddress[] senders = ArrayUtils.addAll(sender1, sender2);

        when(message.getFrom()).thenReturn(senders);
        when(message.getFolder()).thenReturn(folder);
        when(((UIDFolder)folder).getUID(message)).thenReturn(1L);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            singleSenderValidator.validate(imapMessage);
        });
        assertEquals("No single sender was found", thrown.getMessage());
    }

    private InternetAddress[] createInternetAddress(String s) {
        InternetAddress address = new InternetAddress();
        address.setAddress(s);

        InternetAddress[] addresses = new InternetAddress[1];
        addresses[0] = address;
        return addresses;
    }

}
