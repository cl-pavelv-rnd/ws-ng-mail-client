package com.ws.ng.mail_client_connector.message.imap;

import com.ws.ng.mail_client_connector.message.AbstractAddress;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;

@AllArgsConstructor
@Data
public class IMAPAddress implements AbstractAddress {

    private Address address;

    @Override
    public String getEmailAddress() {
        if (address == null || !(address instanceof InternetAddress)) {
            return null;
        }
        return ((InternetAddress) address).getAddress();
    }

    @Override
    public String getDisplayName() {
        if (address == null || !(address instanceof InternetAddress)) {
            return null;
        }
        return ((InternetAddress) address).getPersonal();
    }

}
