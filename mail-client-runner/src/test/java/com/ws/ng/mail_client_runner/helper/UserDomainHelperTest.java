package com.ws.ng.mail_client_runner.helper;

import com.ws.ng.mail_client_connector.helper.UserDomainHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(classes = UserDomainHelper.class)
public class UserDomainHelperTest {

    @Autowired
    private UserDomainHelper userDomainHelper;

    @Test
    public void testGetUserDomain_nullEmailAddress_userDomainEmpty() {
        UserDomainHelper.UserDomain userDomain = userDomainHelper.getUserDomain(null);

        assertNull(userDomain.getUser());
        assertNull(userDomain.getDomain());
    }

    @Test
    public void testGetUserDomain_emptyEmailAddress_userDomainEmpty() {
        UserDomainHelper.UserDomain userDomain = userDomainHelper.getUserDomain("");

        assertNull(userDomain.getUser());
        assertNull(userDomain.getDomain());
    }

    @Test
    public void testGetUserDomain_validEmailAddress_userDomainReturned() {
        UserDomainHelper.UserDomain userDomain = userDomainHelper.getUserDomain("wstu1@rnd-hub.com");

        assertEquals("wstu1", userDomain.getUser());
        assertEquals("rnd-hub.com", userDomain.getDomain());
    }

}
