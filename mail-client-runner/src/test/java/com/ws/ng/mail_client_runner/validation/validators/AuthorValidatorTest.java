package com.ws.ng.mail_client_runner.validation.validators;

import com.r39.ws.cns.datamodel.icd.userDirectory.IcdUser;
import com.ws.ng.mail_client_connector.helper.UserDomainHelper;
import com.ws.ng.mail_client_connector.message.imap.IMAPMessage;
import com.ws.ng.mail_client_runner.runner.UsersCacheRunner;
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

public class AuthorValidatorTest {

    @InjectMocks
    @Autowired
    private AuthorValidator authorValidator;

    @Mock
    private UserDomainHelper userDomainHelper;

    @Mock
    private UsersCacheRunner usersCache;

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
    public void testValidate_validWsUser_noExceptionThrown() throws Exception {
        InternetAddress address = new InternetAddress();
        address.setAddress("wstu1@rnd-hub.com");

        InternetAddress[] addresses = new InternetAddress[1];
        addresses[0] = address;

        when(message.getFrom()).thenReturn(addresses);
        when(message.getFolder()).thenReturn(folder);
        when(((UIDFolder)folder).getUID(message)).thenReturn(1L);
        when(userDomainHelper.getUserDomain("wstu1@rnd-hub.com")).thenReturn(UserDomainHelper.UserDomain.builder().user("wstu1").build());
        when(usersCache.get("wstu1")).thenReturn(new IcdUser());

        authorValidator.validate(imapMessage);
    }

    @Test
    public void testValidate_invalidWsUser_exceptionThrown() throws Exception {
        InternetAddress address = new InternetAddress();
        address.setAddress("wstu1@rnd-hub.com");

        InternetAddress[] addresses = new InternetAddress[1];
        addresses[0] = address;

        when(message.getFrom()).thenReturn(addresses);
        when(message.getFolder()).thenReturn(folder);
        when(((UIDFolder)folder).getUID(message)).thenReturn(1L);
        when(userDomainHelper.getUserDomain("wstu1@rnd-hub.com")).thenReturn(UserDomainHelper.UserDomain.builder().user("wstu1").build());
        when(usersCache.get("wstu1")).thenReturn(null);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            authorValidator.validate(imapMessage);
        });
        assertEquals("Sending user is not a WS user", thrown.getMessage());
    }

}
