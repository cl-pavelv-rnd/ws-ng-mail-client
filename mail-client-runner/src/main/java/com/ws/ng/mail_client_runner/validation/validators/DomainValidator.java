package com.ws.ng.mail_client_runner.validation.validators;

import com.ws.ng.mail_client_connector.message.AbstractAddress;
import com.ws.ng.mail_client_connector.message.AbstractMessage;
import com.ws.ng.mail_client_runner.config.MailClientRunnerConfig;
import com.ws.ng.mail_client_runner.validation.EmailValidatorInterface;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Validate that the email author's domain is an allowed one.
 */
@Component
@Log4j2
public class DomainValidator implements EmailValidatorInterface {

    @Autowired
    private MailClientRunnerConfig config;

    @Override
    public void validate(AbstractMessage message) throws Exception {
        log.info("DomainValidator is running for message " + message.getEmailId());

        String address = Optional.ofNullable(message.getFromAddresses())
                .map(a -> a[0])
                .map(AbstractAddress::getEmailAddress)
                .orElse("");

        int indexOfAt = address.lastIndexOf("@");

        if (indexOfAt > 0) {
            String domain = address.substring(indexOfAt + 1);
            if (!config.getAllowedDomains().contains(domain)) {
                throw new RuntimeException("Email domain is not an allowed domain");
            }
        } else {
            throw new RuntimeException("No domain was found");
        }
    }

}
