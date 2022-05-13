package com.ws.ng.mail_client_connector.message.imap;

import com.ws.ng.mail_client_connector.message.AbstractAddress;
import com.ws.ng.mail_client_connector.message.AbstractAttachment;
import com.ws.ng.mail_client_connector.message.AbstractMessage;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.mail.util.MimeMessageParser;
import org.springframework.util.CollectionUtils;

import javax.activation.DataSource;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.UIDFolder;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class IMAPMessage implements AbstractMessage {

    private Message message;
    private MimeMessageParser parser;

    public IMAPMessage(Message message) {
        this.message = message;
        init();
    }

    public IMAPMessage(Message message, MimeMessageParser parser) {
        this.message = message;
        this.parser = parser;
    }

    private void init() {
        try {
            parser = new MimeMessageParser((MimeMessage) message).parse();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getEmailId() throws MessagingException {
        return String.valueOf(((UIDFolder) (message.getFolder())).getUID(message));
    }

    @Override
    public AbstractAddress[] getFromAddresses() throws MessagingException {
        if (ArrayUtils.isEmpty(message.getFrom())) {
            return null;
        }
        return Arrays.stream(message.getFrom()).map(IMAPAddress::new).toArray(AbstractAddress[]::new);
    }

    @Override
    public AbstractAddress[] getAllRecipients() throws MessagingException {
        if (ArrayUtils.isEmpty(message.getAllRecipients())) {
            return null;
        }
        return Arrays.stream(message.getAllRecipients()).map(IMAPAddress::new).toArray(AbstractAddress[]::new);
    }

    @Override
    public String getSubject() throws MessagingException {
        return message.getSubject();
    }

    @Override
    public Date getSentDate() throws MessagingException {
        return message.getSentDate();
    }

    @Override
    public Date getReceivedDate() throws MessagingException {
        return message.getReceivedDate();
    }

    @Override
    public List<AbstractAddress> getToAddresses() throws Exception {
        if (CollectionUtils.isEmpty(parser.getTo())) {
            return null;
        }
        return parser.getTo().stream().map(IMAPAddress::new).collect(Collectors.toList());
    }

    @Override
    public List<AbstractAddress> getCcAddresses() throws Exception {
        if (CollectionUtils.isEmpty(parser.getCc())) {
            return null;
        }
        return parser.getCc().stream().map(IMAPAddress::new).collect(Collectors.toList());
    }

    @Override
    public List<AbstractAddress> getBccAddresses() throws Exception {
        if (CollectionUtils.isEmpty(parser.getBcc())) {
            return null;
        }
        return parser.getBcc().stream().map(IMAPAddress::new).collect(Collectors.toList());
    }

    @Override
    public Long getComparableValue() {
        try {
            return ((UIDFolder) (message.getFolder())).getUID(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getPlainContent() {
        return parser.getPlainContent();
    }

    @Override
    public String getHtmlContent() {
        return parser.getHtmlContent();
    }

    @Override
    public List<AbstractAttachment> getAttachments() {
        List<DataSource> attachmentList = parser.getAttachmentList();
        return attachmentList.stream().map(IMAPAttachment::new).collect(Collectors.toList());
    }

    @Override
    public void markAsSeen() throws Exception {
        message.setFlag(Flags.Flag.SEEN, true);;
    }

}
