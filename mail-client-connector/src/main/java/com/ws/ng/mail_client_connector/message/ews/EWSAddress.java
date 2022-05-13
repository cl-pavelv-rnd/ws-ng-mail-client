package com.ws.ng.mail_client_connector.message.ews;

import com.ws.ng.mail_client_connector.message.AbstractAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import microsoft.exchange.webservices.data.property.complex.EmailAddress;

@AllArgsConstructor
@Data
public class EWSAddress implements AbstractAddress {

    private EmailAddress address;

    @Override
    public String getEmailAddress() {
       return address.getAddress();
    }

    @Override
    public String getDisplayName() {
        return address.getName();
    }

}
