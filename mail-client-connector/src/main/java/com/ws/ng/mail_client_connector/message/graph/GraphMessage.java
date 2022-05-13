package com.ws.ng.mail_client_connector.message.graph;

import com.microsoft.graph.models.Attachment;
import com.microsoft.graph.models.Message;
import com.microsoft.graph.requests.AttachmentCollectionPage;
import com.ws.ng.mail_client_connector.message.AbstractAddress;
import com.ws.ng.mail_client_connector.message.AbstractAttachment;
import com.ws.ng.mail_client_connector.message.AbstractMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Data
public class GraphMessage implements AbstractMessage {

    private Message message;

    @Override
    public String getEmailId() {
        return message.id;
    }

    @Override
    public AbstractAddress[] getFromAddresses() {
        if (message.from == null) {
            return null;
        }
        return new AbstractAddress[]{new GraphAddress(message.from)};
    }

    @Override
    public AbstractAddress[] getAllRecipients() {
        return Stream.of(getToAddresses(), getCcAddresses(), getBccAddresses())
                .flatMap(Collection::stream)
                .toArray(AbstractAddress[]::new);
    }

    @Override
    public String getSubject() {
        return message.subject;
    }

    @Override
    public Date getSentDate() {
        assert message.sentDateTime != null;
        return Date.from(message.sentDateTime.toInstant());
    }

    @Override
    public Date getReceivedDate() {
        assert message.receivedDateTime != null;
        return Date.from(message.receivedDateTime.toInstant());
    }

    @Override
    public List<AbstractAddress> getToAddresses() {
        if (CollectionUtils.isEmpty(message.toRecipients)) {
            return null;
        }
        return message.toRecipients.stream().map(GraphAddress::new).collect(Collectors.toList());
    }

    @Override
    public List<AbstractAddress> getCcAddresses() {
        if (CollectionUtils.isEmpty(message.ccRecipients)) {
            return null;
        }
        return message.ccRecipients.stream().map(GraphAddress::new).collect(Collectors.toList());
    }

    @Override
    public List<AbstractAddress> getBccAddresses() {
        if (CollectionUtils.isEmpty(message.bccRecipients)) {
            return null;
        }
        return message.bccRecipients.stream().map(GraphAddress::new).collect(Collectors.toList());
    }

    @Override
    public Long getComparableValue() {
        return getReceivedDate().getTime();
    }

    @Override
    public String getPlainContent() {
        return Optional.ofNullable(message.body).map(m -> m.content).orElse(""); // TODO @pavel
    }

    @Override
    public String getHtmlContent() {
        return Optional.ofNullable(message.body).map(m -> m.content).orElse(""); // TODO @pavel
    }

    @Override
    public List<AbstractAttachment> getAttachments() {
        if (message.hasAttachments == null || !message.hasAttachments) {
            return new ArrayList<>();
        }

        List<Attachment> allAttachments = new ArrayList<>();

        AttachmentCollectionPage attachmentsPage = message.attachments;
        while (attachmentsPage != null && attachmentsPage.getCount() != null && attachmentsPage.getCount() != 0) {
            List<Attachment> currentPage = attachmentsPage.getCurrentPage();
            allAttachments.addAll(currentPage);
        }

        return allAttachments.stream().map(GraphAttachment::new).collect(Collectors.toList());
    }

    @Override
    public void markAsSeen() {
        message.isRead = true;
    }

}
