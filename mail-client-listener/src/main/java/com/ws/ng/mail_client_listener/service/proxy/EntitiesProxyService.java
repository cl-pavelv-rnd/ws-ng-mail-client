package com.ws.ng.mail_client_listener.service.proxy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.r39.ws.cns.datamodel.icd.entities.IcdEmail;
import com.ws.ng.mail_client_datamodel.email.EmailEvent;
import com.ws.ng.mail_client_datamodel.header.WsUserHeaders;
import com.ws.ng.mail_client_listener.common.EntitiesReply;
import com.ws.ng.mail_client_listener.config.MailClientListenerConfig;
import com.ws.ng.mail_client_listener.transformer.datamodel.EmailEntityTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class EntitiesProxyService {

    @Autowired
    private MailClientListenerConfig config;

    @Autowired
    private EmailEntityTransformer transformer;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpClient httpClient;

    public EntitiesReply createEmailEntity(EmailEvent emailEvent) throws Exception {
        var url = config.getEntitiesUrl() + "/Email";

        IcdEmail body = transformer.toIcd(emailEvent.getEmailMessage());

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

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(httpResponse.body(), EntitiesReply.class);
    }

}
