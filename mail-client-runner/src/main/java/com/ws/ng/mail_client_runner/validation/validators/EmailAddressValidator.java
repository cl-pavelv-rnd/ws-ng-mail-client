package com.ws.ng.mail_client_runner.validation.validators;

import com.ws.ng.mail_client_connector.message.AbstractAddress;
import com.ws.ng.mail_client_connector.message.AbstractMessage;
import com.ws.ng.mail_client_runner.validation.EmailValidatorInterface;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Validate that the email address is a valid one.
 */
@Component
@Log4j2
public class EmailAddressValidator implements EmailValidatorInterface {

    @Override
    public void validate(AbstractMessage message) throws Exception {
        log.info("EmailAddressValidator is running for message " + message.getEmailId());

        AbstractAddress[] addresses = ArrayUtils.addAll(message.getFromAddresses(), message.getAllRecipients());
        List<String> emailAddresses = Arrays
                .stream(addresses)
                .map(AbstractAddress::getEmailAddress)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        for (String emailAddress : emailAddresses) {
            if (!validateAddress(emailAddress)) {
                throw new RuntimeException("Invalid email address");
            }
        }
    }

    private boolean validateAddress(String emailAddress) {
        return EmailValidator.getInstance().isValid(emailAddress);
    }

}
