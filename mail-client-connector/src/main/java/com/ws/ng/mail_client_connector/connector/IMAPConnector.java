package com.ws.ng.mail_client_connector.connector;

import com.ws.ng.mail_client_connector.message.AbstractMessage;
import com.ws.ng.mail_client_connector.message.imap.IMAPMessage;
import com.ws.ng.mail_client_connector.transformer.EmailMessageTransformer;
import com.ws.ng.mail_client_datamodel.email.IcdEmailMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.mail.*;
import java.util.Arrays;
import java.util.Properties;

@Component
@Profile("!bina-prod")
@Log4j2
public class IMAPConnector implements EmailServerConnector {

    @Autowired
    private EmailMessageTransformer transformer;

    private Store store;
    private Folder folder;

    private String host;
    private String port;
    private String user;
    private String password;
    private String protocol;
    private String folderName;

    @Override
    public void init(Properties properties) {
        this.host = properties.getProperty("host");
        this.port = properties.getProperty("port");
        this.user = properties.getProperty("user");
        this.password = properties.getProperty("password");
        this.protocol = properties.getProperty("protocol");
        this.folderName = properties.getProperty("folderName");
    }

    @Override
    public void connect() throws Exception {
        log.info("Connecting to email server...");

        Session session = Session.getDefaultInstance(new Properties());

        store = session.getStore(protocol);
        store.connect(host, Integer.parseInt(port), user, password);

        log.info("Connected!");
    }

    @Override
    public AbstractMessage[] searchEmails(Long from) throws Exception {
        log.info("Received searchEmails() with from {}", from);

        folder = openFolder();

        Message[] messages = ((UIDFolder) folder).getMessagesByUID(from, UIDFolder.MAXUID);
        sortMessages((UIDFolder) folder, messages);

        return Arrays.stream(messages).map(IMAPMessage::new).toArray(AbstractMessage[]::new);
    }

    @Override
    public AbstractMessage getEmailMessageById(String id) throws Exception {
        log.info("Received getEmailMessageById() with id {}", id);

        try (Folder folder = openFolder()) {
            var uidFolder = (UIDFolder) folder;
            Message message = uidFolder.getMessageByUID(Long.parseLong(id));

            return new IMAPMessage(message);
        }
    }

    @Override
    public synchronized void populateEmailBody(IcdEmailMessage emailMessage) throws Exception {
        log.info("Received populateEmailBody() with id {}", emailMessage.getId());

        try (Folder folder = openFolder()) {
            var uidFolder = (UIDFolder) folder;
            Message message = uidFolder.getMessageByUID(Long.parseLong(emailMessage.getId()));

            populateEmailBody(emailMessage, new IMAPMessage(message));
        }
    }

    @Override
    public void close() throws MessagingException {
        folder.close();
        store.close();
    }

    private Folder openFolder() throws Exception {
        if (!store.isConnected()) {
            log.info("Store was not connected...");

            store.close();
            connect();
        }

        folder = store.getFolder(folderName);
        folder.open(Folder.READ_WRITE);
        return folder;
    }

    private void sortMessages(UIDFolder uidFolder, Message[] messages) {
        Arrays.sort(messages, (m1, m2) -> {
            try {
                return Long.compare(uidFolder.getUID(m1), uidFolder.getUID(m2));
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
