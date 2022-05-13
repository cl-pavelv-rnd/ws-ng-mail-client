package com.ws.ng.mail_client_connector.message.ews;

import com.ws.ng.mail_client_connector.message.AbstractAttachment;
import lombok.AllArgsConstructor;
import lombok.Data;
import microsoft.exchange.webservices.data.property.complex.FileAttachment;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@AllArgsConstructor
@Data
public class EWSAttachment implements AbstractAttachment {

    private FileAttachment attachment;

    @Override
    public String getName() {
        return attachment.getName();
    }

    @Override
    public String getType() {
        return attachment.getContentType();
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(attachment.getContent());
    }

}
