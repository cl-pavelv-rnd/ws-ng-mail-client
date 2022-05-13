package com.ws.ng.mail_client_connector.message.imap;

import com.ws.ng.mail_client_connector.message.AbstractAttachment;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.activation.DataSource;
import java.io.IOException;
import java.io.InputStream;

@AllArgsConstructor
@Data
public class IMAPAttachment implements AbstractAttachment {

    private DataSource dataSource;

    @Override
    public String getName() {
        return dataSource.getName();
    }

    @Override
    public String getType() {
        return dataSource.getContentType();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return dataSource.getInputStream();
    }

}
