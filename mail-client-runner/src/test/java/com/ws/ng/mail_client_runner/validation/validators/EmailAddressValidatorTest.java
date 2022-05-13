package com.ws.ng.mail_client_runner.validation.validators;

import com.ws.ng.mail_client_connector.message.imap.IMAPMessage;
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

public class EmailAddressValidatorTest {

    @InjectMocks
    @Autowired
    private EmailAddressValidator emailAddressValidator;

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
    public void testValidate_validEmailAddress_noExceptionThrown() throws Exception {
        InternetAddress[] valid1 = createInternetAddress("wstu1@rnd-hub.com");
        InternetAddress[] valid2 = createInternetAddress("wstu2@rnd-hub.com");

        when(message.getFrom()).thenReturn(valid1);
        when(message.getAllRecipients()).thenReturn(valid2);
        when(message.getFolder()).thenReturn(folder);
        when(((UIDFolder)folder).getUID(message)).thenReturn(1L);

        emailAddressValidator.validate(imapMessage);
    }

    @Test
    public void testValidate_invalidEmailAddress_exceptionThrown() throws Exception {
        InternetAddress[] valid = createInternetAddress("wstu1@rnd-hub.com");
        InternetAddress[] invalid = createInternetAddress("wstu1@walla");

        when(message.getFrom()).thenReturn(valid);
        when(message.getAllRecipients()).thenReturn(invalid);
        when(message.getFolder()).thenReturn(folder);
        when(((UIDFolder)folder).getUID(message)).thenReturn(1L);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            emailAddressValidator.validate(imapMessage);
        });
        assertEquals("Invalid email address", thrown.getMessage());
    }

    private InternetAddress[] createInternetAddress(String s) {
        InternetAddress address = new InternetAddress();
        address.setAddress(s);

        InternetAddress[] addresses = new InternetAddress[1];
        addresses[0] = address;
        return addresses;
    }

}
