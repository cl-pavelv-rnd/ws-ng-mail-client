package com.ws.ng.mail_client_datamodel.email;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class IcdEmailMessage {

    private String id;
    private String subject;
    private String plainContent;
    private String htmlContent;
    private Date sentDate;
    private Date receivedDate;
    private IcdEmailAddress from;
    private List<IcdEmailAddress> to;
    private List<IcdEmailAddress> cc;
    private List<IcdEmailAddress> bcc;
    private List<IcdAttachment> attachments;

}
