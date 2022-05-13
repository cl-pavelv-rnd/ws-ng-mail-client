package com.ws.ng.mail_client_datamodel.email;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class IcdEmailAddress {

    private String username;
    private String address;
    private String personal;

}
