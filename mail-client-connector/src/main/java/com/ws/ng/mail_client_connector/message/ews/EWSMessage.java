package com.ws.ng.mail_client_connector.message.ews;

import com.ws.ng.mail_client_connector.message.AbstractAddress;
import com.ws.ng.mail_client_connector.message.AbstractAttachment;
import com.ws.ng.mail_client_connector.message.AbstractMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.property.complex.FileAttachment;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Data
public class EWSMessage implements AbstractMessage {

    private EmailMessage message;

    @Override
    public String getEmailId() throws Exception {
        return message.getId().getUniqueId();
    }

    @Override
    public AbstractAddress[] getFromAddresses() throws Exception {
        if (message.getFrom() == null) {
            return null;
        }
        return new AbstractAddress[]{new EWSAddress(message.getFrom())};
    }

    @Override
    public AbstractAddress[] getAllRecipients() throws Exception {
        return Stream.of(getToAddresses(), getCcAddresses(), getBccAddresses())
                .flatMap(Collection::stream)
                .toArray(AbstractAddress[]::new);
    }

    @Override
    public String getSubject() throws Exception {
        return message.getSubject();
    }

    @Override
    public Date getSentDate() throws Exception {
        return message.getDateTimeSent();
    }

    @Override
    public Date getReceivedDate() throws Exception {
        return message.getDateTimeReceived();
    }

    @Override
    public List<AbstractAddress> getToAddresses() throws Exception {
        if (message.getToRecipients() == null || CollectionUtils.isEmpty(message.getToRecipients().getItems())) {
            return null;
        }
        return message.getToRecipients().getItems().stream().map(EWSAddress::new).collect(Collectors.toList());
    }

    @Override
    public List<AbstractAddress> getCcAddresses() throws Exception {
        if (message.getCcRecipients() == null || CollectionUtils.isEmpty(message.getCcRecipients().getItems())) {
            return null;
        }
        return message.getCcRecipients().getItems().stream().map(EWSAddress::new).collect(Collectors.toList());
    }

    @Override
    public List<AbstractAddress> getBccAddresses() throws Exception {
        if (message.getBccRecipients() == null || CollectionUtils.isEmpty(message.getBccRecipients().getItems())) {
            return null;
        }
        return message.getBccRecipients().getItems().stream().map(EWSAddress::new).collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public Long getComparableValue() {
        return getReceivedDate().getTime();
    }

    @Override
    @SneakyThrows
    public String getPlainContent() {
        return message.getUniqueBody().toString();
    }

    @Override
    @SneakyThrows
    public String getHtmlContent() {
        return message.getUniqueBody().toString();
    }

    @Override
    @SneakyThrows
    public List<AbstractAttachment> getAttachments() {
        if (message.getAttachments() == null || CollectionUtils.isEmpty(message.getAttachments().getItems())) {
            return new ArrayList<>();
        }

        return message.getAttachments().getItems()
                .stream()
                .filter(a -> a instanceof FileAttachment)
                .map(a -> (FileAttachment) a)
                .peek(a -> {
                    try {
                        a.load();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .map(EWSAttachment::new)
                .collect(Collectors.toList());
    }

    @Override
    public void markAsSeen() throws Exception {
       message.setIsRead(true);
    }

}
