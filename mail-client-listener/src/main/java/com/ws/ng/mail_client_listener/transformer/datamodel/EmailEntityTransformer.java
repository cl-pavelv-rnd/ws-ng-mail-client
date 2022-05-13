package com.ws.ng.mail_client_listener.transformer.datamodel;

import com.r39.ws.cns.datamodel.icd.common.IcdAttachment;
import com.r39.ws.cns.datamodel.icd.common.IcdEmailAddress;
import com.r39.ws.cns.datamodel.icd.entities.IcdEmail;
import com.ws.ng.mail_client_datamodel.email.IcdEmailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Transformer from internal ICD objects to data model objects.
 */
@Component
public class EmailEntityTransformer {

    @Autowired
    private EmailEntityAddressTransformer emailEntityAddressTransformer;

    public IcdEmail toIcd(IcdEmailMessage emailMessage) {
        var icdEmail = new IcdEmail();
        icdEmail.setSubject(emailMessage.getSubject());
        icdEmail.setPlainContent(emailMessage.getPlainContent());
        icdEmail.setHtmlContent(emailMessage.getHtmlContent());
        icdEmail.setSentDate(emailMessage.getSentDate());
        icdEmail.setReceivedDate(emailMessage.getReceivedDate());

        if (emailMessage.getFrom() != null) {
            icdEmail.setFrom(emailEntityAddressTransformer.toIcd(emailMessage.getFrom()));
        }

        if (!CollectionUtils.isEmpty(emailMessage.getTo())) {
            icdEmail.setTo(toIcdEmailAddressList(emailMessage.getTo()));
        }

        if (!CollectionUtils.isEmpty(emailMessage.getCc())) {
            icdEmail.setCc(toIcdEmailAddressList(emailMessage.getCc()));
        }

        if (!CollectionUtils.isEmpty(emailMessage.getBcc())) {
            icdEmail.setBcc(toIcdEmailAddressList(emailMessage.getBcc()));
        }

        if (!CollectionUtils.isEmpty(emailMessage.getAttachments())) {
            icdEmail.setAttachmentList(toIcdAttachmentList(emailMessage.getAttachments()));
        }

        return icdEmail;
    }

    private List<IcdEmailAddress> toIcdEmailAddressList(List<com.ws.ng.mail_client_datamodel.email.IcdEmailAddress> addresses) {
        return addresses
                .stream()
                .map(emailEntityAddressTransformer::toIcd)
                .collect(Collectors.toList());
    }

    private List<IcdAttachment> toIcdAttachmentList(List<com.ws.ng.mail_client_datamodel.email.IcdAttachment> attachments) {
        return attachments
                .stream()
                .map(a -> {
                    var icdAttachment = new IcdAttachment();
                    icdAttachment.setName(a.getName());
                    icdAttachment.setType(a.getType());
                    return icdAttachment;
                })
                .collect(Collectors.toList());
    }

}
