package com.ws.ng.mail_client_connector.message;

import java.util.Date;
import java.util.List;

public interface AbstractMessage {

    String getEmailId() throws Exception;

    AbstractAddress[] getFromAddresses() throws Exception;

    AbstractAddress[] getAllRecipients() throws Exception;

    String getSubject() throws Exception;

    Date getSentDate() throws Exception;

    Date getReceivedDate() throws Exception;

    List<AbstractAddress> getToAddresses() throws Exception;

    List<AbstractAddress> getCcAddresses() throws Exception;

    List<AbstractAddress> getBccAddresses() throws Exception;

    Long getComparableValue();

    String getPlainContent();

    String getHtmlContent();

    List<AbstractAttachment> getAttachments();

    void markAsSeen() throws Exception;

}
