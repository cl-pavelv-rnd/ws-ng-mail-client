package com.ws.ng.mail_client_runner.validation.validators;

import com.ws.ng.mail_client_connector.message.AbstractMessage;
import com.ws.ng.mail_client_runner.validation.EmailValidatorInterface;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * Validate that the email message contains a sending address.
 */
@Component
@Log4j2
public class SingleSenderValidator implements EmailValidatorInterface {

    @Override
    public void validate(AbstractMessage message) throws Exception {
        log.info("SingleSenderValidator is running for message " + message.getEmailId());

        if (message.getFromAddresses() == null || message.getFromAddresses().length != 1) {
            throw new RuntimeException("No single sender was found");
        }
    }

}
