package com.ws.ng.mail_client_runner.validation;

import com.ws.ng.mail_client_connector.message.AbstractMessage;

/**
 * Every class that implements this interface will be invoked per email validation.
 */
public interface EmailValidatorInterface {

    void validate(AbstractMessage message) throws Exception;

}
