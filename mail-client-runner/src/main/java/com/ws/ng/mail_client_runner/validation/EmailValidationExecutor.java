package com.ws.ng.mail_client_runner.validation;

import com.ws.ng.mail_client_connector.message.AbstractMessage;
import com.ws.ng.mail_client_connector.transformer.EmailMessageTransformer;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * A validation executor class that runs relevant validations of the email message.
 * Only validated messages are then returned.
 */
@Component
@Log4j2
public class EmailValidationExecutor {

    @Autowired
    private List<EmailValidatorInterface> validators; // all implementors are injected by Spring

    @Autowired
    private EmailMessageTransformer transformer;

    public List<AbstractMessage> getValidatedEmailMessages(AbstractMessage[] messages) throws Exception {
        var validatedEmailMessages = new ArrayList<AbstractMessage>();
        for (AbstractMessage message : messages) {
            try {
                for (EmailValidatorInterface validator : validators) {
                    validator.validate(message);
                }

                validatedEmailMessages.add(message);
            } catch (Exception e) {
                log.error("Validation failed for message {}", transformer.toIcd(message));
                log.error("Invalid email message with id " + message.getEmailId(), e);
            }
        }
        return validatedEmailMessages;
    }

}
