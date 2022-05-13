package com.ws.ng.mail_client_runner.validation.validators;

import com.ws.ng.mail_client_connector.helper.UserDomainHelper;
import com.ws.ng.mail_client_connector.message.AbstractAddress;
import com.ws.ng.mail_client_connector.message.AbstractMessage;
import com.ws.ng.mail_client_runner.runner.UsersCacheRunner;
import com.ws.ng.mail_client_runner.validation.EmailValidatorInterface;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Validate that the email's author is a valid WS user.
 */
@Component
@Log4j2
public class AuthorValidator implements EmailValidatorInterface {

    @Autowired
    private UserDomainHelper userDomainHelper;

    @Autowired
    private UsersCacheRunner usersCache;

    @Override
    public void validate(AbstractMessage message) throws Exception {
        log.info("AuthorValidator is running for message " + message.getEmailId());

        String emailAddress = Optional.ofNullable(message.getFromAddresses())
                .map(a -> a[0])
                .map(AbstractAddress::getEmailAddress)
                .orElse(null);

        UserDomainHelper.UserDomain userDomain = userDomainHelper.getUserDomain(emailAddress);
        if (usersCache.get(userDomain.getUser()) == null) {
            throw new RuntimeException("Sending user is not a WS user");
        }
    }

}
