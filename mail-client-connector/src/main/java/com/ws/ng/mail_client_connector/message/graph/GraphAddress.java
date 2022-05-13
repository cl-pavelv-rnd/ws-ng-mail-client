package com.ws.ng.mail_client_connector.message.graph;

import com.microsoft.graph.models.Recipient;
import com.ws.ng.mail_client_connector.message.AbstractAddress;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GraphAddress implements AbstractAddress {

    private Recipient recipient;

    @Override
    public String getEmailAddress() {
        if (recipient.emailAddress == null) {
            return null;
        }
        return recipient.emailAddress.address;
    }

    @Override
    public String getDisplayName() {
        if (recipient.emailAddress == null) {
            return null;
        }
        return recipient.emailAddress.name;
    }

}
