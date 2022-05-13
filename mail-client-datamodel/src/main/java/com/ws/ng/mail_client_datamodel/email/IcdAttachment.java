package com.ws.ng.mail_client_datamodel.email;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.io.InputStream;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class IcdAttachment {

    private String name;
    private String type;
    private String owner;
    private String contentId;
    private InputStream inputStream;

}
