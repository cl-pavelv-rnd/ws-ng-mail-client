package com.ws.ng.mail_client_connector.message;

import java.io.IOException;
import java.io.InputStream;

public interface AbstractAttachment {

    String getName();

    String getType();

    InputStream getInputStream() throws IOException;

}
