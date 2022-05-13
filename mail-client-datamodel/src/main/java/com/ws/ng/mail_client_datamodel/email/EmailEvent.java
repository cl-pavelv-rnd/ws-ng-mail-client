package com.ws.ng.mail_client_datamodel.email;

import com.ws.ng.mail_client_datamodel.header.WsUserHeaders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailEvent {

    private IcdEmailMessage emailMessage;
    private WsUserHeaders headers;

}
