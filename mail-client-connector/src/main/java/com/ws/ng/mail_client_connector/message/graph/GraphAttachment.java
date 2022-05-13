package com.ws.ng.mail_client_connector.message.graph;

import com.microsoft.graph.models.Attachment;
import com.microsoft.graph.models.FileAttachment;
import com.ws.ng.mail_client_connector.message.AbstractAttachment;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@AllArgsConstructor
@Data
public class GraphAttachment implements AbstractAttachment {

    private Attachment attachment;

    @Override
    public String getName() {
        return attachment.name;
    }

    @Override
    public String getType() {
        return attachment.contentType;
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(((FileAttachment) attachment).contentBytes);
    }

}
