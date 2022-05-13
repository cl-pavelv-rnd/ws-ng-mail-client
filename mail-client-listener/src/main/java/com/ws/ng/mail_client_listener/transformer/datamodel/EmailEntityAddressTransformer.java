package com.ws.ng.mail_client_listener.transformer.datamodel;

import com.r39.ws.cns.datamodel.icd.common.IcdEmailAddress;
import org.springframework.stereotype.Component;

/**
 * Transformer from internal ICD objects to data model objects.
 */
@Component
public class EmailEntityAddressTransformer {

    public IcdEmailAddress toIcd(com.ws.ng.mail_client_datamodel.email.IcdEmailAddress address) {
        var icdEmailAddress = new IcdEmailAddress();
        icdEmailAddress.setAddress(address.getAddress());
        icdEmailAddress.setPersonal(address.getPersonal());
        return icdEmailAddress;
    }

}
