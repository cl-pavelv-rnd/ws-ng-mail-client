package com.ws.ng.mail_client_connector.transformer;

import com.ws.ng.mail_client_avro.EmailAddress;
import com.ws.ng.mail_client_connector.helper.UserDomainHelper;
import com.ws.ng.mail_client_connector.message.AbstractAddress;
import com.ws.ng.mail_client_datamodel.email.IcdEmailAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Transformer from mail objects to AVRO / internal ICD objects.
 */
@Component
public class EmailAddressTransformer {

    @Autowired
    private UserDomainHelper userDomainHelper;

    public EmailAddress toAvro(AbstractAddress abstractAddress) {
        UserDomainHelper.UserDomain userDomain = userDomainHelper.getUserDomain(abstractAddress.getEmailAddress());

        var emailAddress = new EmailAddress();
        emailAddress.setAddress(abstractAddress.getEmailAddress());
        emailAddress.setDisplayName(abstractAddress.getDisplayName());
        emailAddress.setUsername(userDomain.getUser());
        return emailAddress;
    }

    public IcdEmailAddress toIcd(AbstractAddress abstractAddress) {
        UserDomainHelper.UserDomain userDomain = userDomainHelper.getUserDomain(abstractAddress.getEmailAddress());

        var icdEmailAddress = new IcdEmailAddress();
        icdEmailAddress.setAddress(abstractAddress.getEmailAddress());
        icdEmailAddress.setPersonal(abstractAddress.getDisplayName());
        icdEmailAddress.setUsername(userDomain.getUser());
        return icdEmailAddress;
    }

}
