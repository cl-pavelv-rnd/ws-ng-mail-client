package com.ws.ng.mail_client_listener.service.proxy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.r39.ws.cns.datamodel.icd.IcdLink;
import com.ws.ng.mail_client_datamodel.email.EmailEvent;
import com.ws.ng.mail_client_datamodel.header.WsUserHeaders;
import com.ws.ng.mail_client_listener.common.EntitiesReply;
import com.ws.ng.mail_client_listener.config.MailClientListenerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LinksProxyService {

    @Autowired
    private MailClientListenerConfig config;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpClient httpClient;

    public void createFileLinks(
            EntitiesReply emailEntityReply,
            List<EntitiesReply> fileEntitiesReplies,
            EmailEvent emailEvent) throws Exception {
        if (fileEntitiesReplies.size() > 0) {
            var url = config.getLinksUrl() + "/links/_bulk/create";

            List<IcdLink> body = fileEntitiesReplies
                    .stream()
                    .map(e -> createLink(emailEntityReply, e))
                    .collect(Collectors.toList());

            WsUserHeaders userHeaders = emailEvent.getHeaders();

            HttpRequest httpRequest = HttpRequest
                    .newBuilder()
                    .uri(new URI(url))
                    .header(WsUserHeaders.USER_NAME, userHeaders.getUsername())
                    .header(WsUserHeaders.CLASSIFICATION_VALUE, userHeaders.getClassification())
                    .header(WsUserHeaders.DIVISION_ID, userHeaders.getDivisionId())
                    .header(WsUserHeaders.POSITION_ID, userHeaders.getPositionId())
                    .header(WsUserHeaders.ROLES, userHeaders.getRoles())
                    .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(body)))
                    .build();

            httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        }
    }

    private IcdLink createLink(EntitiesReply emailEntityReply, EntitiesReply fileEntityReply) {
        var icdLink = new IcdLink();
        icdLink.sourceId = emailEntityReply.getEntityId();
        icdLink.sourceType = emailEntityReply.getEntityType();
        icdLink.targetId = fileEntityReply.getEntityId();
        icdLink.targetType = fileEntityReply.getEntityType();
        icdLink.relationType = "email_attachment_rel";
        icdLink.relationValue = "email_attachment_rel_1";
        return icdLink;
    }

}
