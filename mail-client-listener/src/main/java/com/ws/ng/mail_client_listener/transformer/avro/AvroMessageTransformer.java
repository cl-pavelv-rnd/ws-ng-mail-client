package com.ws.ng.mail_client_listener.transformer.avro;

import com.ws.ng.mail_client_avro.EmailAddress;
import com.ws.ng.mail_client_avro.ExternalEmailMessageEvent;
import com.ws.ng.mail_client_datamodel.email.IcdEmailAddress;
import com.ws.ng.mail_client_datamodel.email.IcdEmailMessage;
import com.ws.ng.mail_client_datamodel.header.WsUserHeaders;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Transformer from AVRO objects to internal ICD objects.
 */
@Component
public class AvroMessageTransformer {

    @Autowired
    private AvroAddressTransformer avroAddressTransformer;

    public WsUserHeaders toHeaders(Headers headers) {
        Map<String, String> headersMap = Arrays
                .stream(headers.toArray())
                .collect(Collectors.toMap(Header::key, v -> new String(v.value(), StandardCharsets.UTF_8)));

        return new WsUserHeaders(
                headersMap.get(WsUserHeaders.USER_NAME),
                headersMap.get(WsUserHeaders.CLASSIFICATION_VALUE),
                headersMap.get(WsUserHeaders.POSITION_ID),
                headersMap.get(WsUserHeaders.DIVISION_ID),
                headersMap.get(WsUserHeaders.ROLES)
        );
    }

    public IcdEmailMessage toIcd(ExternalEmailMessageEvent event) {
        var icdEmailMessage = new IcdEmailMessage();
        icdEmailMessage.setId(event.getId());
        icdEmailMessage.setSubject(event.getSubject());
        icdEmailMessage.setSentDate(new Date(event.getSentDate()));
        icdEmailMessage.setReceivedDate(new Date(event.getReceivedDate()));

        if (event.getFrom() != null) {
            icdEmailMessage.setFrom(avroAddressTransformer.toIcd(event.getFrom()));
        }

        if (!CollectionUtils.isEmpty(event.getTo())) {
            icdEmailMessage.setTo(toIcdEmailAddressList(event.getTo()));
        }

        if (!CollectionUtils.isEmpty(event.getCc())) {
            icdEmailMessage.setCc(toIcdEmailAddressList(event.getCc()));
        }

        if (!CollectionUtils.isEmpty(event.getBcc())) {
            icdEmailMessage.setBcc(toIcdEmailAddressList(event.getBcc()));
        }

        return icdEmailMessage;
    }

    private List<IcdEmailAddress> toIcdEmailAddressList(List<EmailAddress> addresses) {
        return addresses
                .stream()
                .map(avroAddressTransformer::toIcd)
                .collect(Collectors.toList());
    }

}
