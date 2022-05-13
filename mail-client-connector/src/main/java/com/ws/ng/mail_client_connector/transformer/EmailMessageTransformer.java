package com.ws.ng.mail_client_connector.transformer;

import com.ws.ng.mail_client_avro.EmailAddress;
import com.ws.ng.mail_client_avro.ExternalEmailMessageEvent;
import com.ws.ng.mail_client_connector.message.AbstractAddress;
import com.ws.ng.mail_client_connector.message.AbstractAttachment;
import com.ws.ng.mail_client_connector.message.AbstractMessage;
import com.ws.ng.mail_client_datamodel.email.IcdAttachment;
import com.ws.ng.mail_client_datamodel.email.IcdEmailAddress;
import com.ws.ng.mail_client_datamodel.email.IcdEmailMessage;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Transformer from mail objects to AVRO / internal ICD objects.
 */
@Component
public class EmailMessageTransformer {

    @Autowired
    private EmailAddressTransformer emailAddressTransformer;

    public ExternalEmailMessageEvent toAvro(AbstractMessage message) throws RuntimeException {
        try {
            var emailMessageEvent = new ExternalEmailMessageEvent();
            emailMessageEvent.setId(message.getEmailId());
            emailMessageEvent.setSubject(message.getSubject());
            emailMessageEvent.setSentDate(message.getSentDate().getTime());
            emailMessageEvent.setReceivedDate(message.getReceivedDate().getTime());

            if (ArrayUtils.isNotEmpty(message.getFromAddresses())) {
                emailMessageEvent.setFrom(emailAddressTransformer.toAvro(message.getFromAddresses()[0]));
            }

            if (!CollectionUtils.isEmpty(message.getToAddresses())) {
                emailMessageEvent.setTo(toAvroEmailAddressList(message.getToAddresses()));
            }

            if (!CollectionUtils.isEmpty(message.getCcAddresses())) {
                emailMessageEvent.setCc(toAvroEmailAddressList(message.getCcAddresses()));
            }

            if (!CollectionUtils.isEmpty(message.getBccAddresses())) {
                emailMessageEvent.setBcc(toAvroEmailAddressList(message.getBccAddresses()));
            }

            return emailMessageEvent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<EmailAddress> toAvroEmailAddressList(List<AbstractAddress> abstractAddresses) {
        return abstractAddresses
                .stream()
                .map(emailAddressTransformer::toAvro)
                .collect(Collectors.toList());
    }

    public IcdEmailMessage toIcd(AbstractMessage message) throws RuntimeException {
        try {
            var icdEmailMessage = new IcdEmailMessage();
            icdEmailMessage.setId(message.getEmailId());
            icdEmailMessage.setSubject(message.getSubject());
            icdEmailMessage.setSentDate(message.getSentDate());
            icdEmailMessage.setReceivedDate(message.getReceivedDate());

            if (ArrayUtils.isNotEmpty(message.getFromAddresses())) {
                icdEmailMessage.setFrom(emailAddressTransformer.toIcd(message.getFromAddresses()[0]));
            }

            if (!CollectionUtils.isEmpty(message.getToAddresses())) {
                icdEmailMessage.setTo(toIcdEmailAddressList(message.getToAddresses()));
            }

            if (!CollectionUtils.isEmpty(message.getCcAddresses())) {
                icdEmailMessage.setCc(toIcdEmailAddressList(message.getCcAddresses()));
            }

            if (!CollectionUtils.isEmpty(message.getBccAddresses())) {
                icdEmailMessage.setBcc(toIcdEmailAddressList(message.getBccAddresses()));
            }

            List<AbstractAttachment> attachments = message.getAttachments();
            if (!CollectionUtils.isEmpty(attachments)) {
                icdEmailMessage.setAttachments(toIcdAttachmentList(attachments));
            }

            return icdEmailMessage;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<IcdEmailAddress> toIcdEmailAddressList(List<AbstractAddress> abstractAddresses) {
        return abstractAddresses
                .stream()
                .map(emailAddressTransformer::toIcd)
                .collect(Collectors.toList());
    }

    private List<IcdAttachment> toIcdAttachmentList(List<AbstractAttachment> attachments) {
        return attachments
                .stream()
                .map(a -> IcdAttachment.builder().type(a.getType()).name(a.getName()).build())
                .collect(Collectors.toList());
    }

}
