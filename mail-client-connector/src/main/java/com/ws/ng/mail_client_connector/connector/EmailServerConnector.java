package com.ws.ng.mail_client_connector.connector;

import com.ws.ng.mail_client_connector.message.AbstractAttachment;
import com.ws.ng.mail_client_connector.message.AbstractMessage;
import com.ws.ng.mail_client_datamodel.email.IcdAttachment;
import com.ws.ng.mail_client_datamodel.email.IcdEmailMessage;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public interface EmailServerConnector {

    void init(Properties properties);

    void connect() throws Exception;

    AbstractMessage[] searchEmails(Long from) throws Exception;

    AbstractMessage getEmailMessageById(String id) throws Exception;

    void populateEmailBody(IcdEmailMessage emailMessage) throws Exception;

    default void populateEmailBody(IcdEmailMessage emailMessage, AbstractMessage abstractMessage) throws Exception {
        populatePlainContent(emailMessage, abstractMessage);
        populateHtmlContent(emailMessage, abstractMessage);
        populateAttachments(emailMessage, abstractMessage);
    }

    default void populatePlainContent(IcdEmailMessage emailMessage, AbstractMessage abstractMessage) throws Exception {
        emailMessage.setPlainContent(abstractMessage.getPlainContent());
    }

    default void populateHtmlContent(IcdEmailMessage emailMessage, AbstractMessage abstractMessage) throws Exception {
        emailMessage.setHtmlContent(abstractMessage.getHtmlContent());
    }

    default void populateAttachments(IcdEmailMessage emailMessage, AbstractMessage abstractMessage) throws Exception {
        List<AbstractAttachment> attachmentList = abstractMessage.getAttachments();
        if (!CollectionUtils.isEmpty(attachmentList)) {
            List<IcdAttachment> attachments = new ArrayList<>();
            for (AbstractAttachment abstractAttachment : attachmentList) {
                var icdAttachment = IcdAttachment.builder()
                        .name(abstractAttachment.getName())
                        .type(abstractAttachment.getType())
                        .inputStream(abstractAttachment.getInputStream())
                        .owner(emailMessage.getFrom().getUsername())
                        .build();
                attachments.add(icdAttachment);
            }
            emailMessage.setAttachments(attachments);
        } else {
            emailMessage.setAttachments(Collections.emptyList());
        }
    }

    void close() throws Exception;

}
