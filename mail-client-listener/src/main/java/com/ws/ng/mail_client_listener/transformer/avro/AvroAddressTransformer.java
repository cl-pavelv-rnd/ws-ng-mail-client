package com.ws.ng.mail_client_listener.transformer.avro;

import com.ws.ng.mail_client_avro.EmailAddress;
import com.ws.ng.mail_client_datamodel.email.IcdEmailAddress;
import org.springframework.stereotype.Component;

/**
 * Transformer from AVRO objects to internal ICD objects.
 */
@Component
public class AvroAddressTransformer {

    public IcdEmailAddress toIcd(EmailAddress address) {
        var icdEmailAddress = new IcdEmailAddress();
        icdEmailAddress.setAddress(address.getAddress());
        icdEmailAddress.setPersonal(address.getDisplayName());
        icdEmailAddress.setUsername(address.getUsername());
        return icdEmailAddress;
    }

}
