package com.ws.ng.mail_client_listener.service.proxy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.r39.ws.cns.datamodel.doc.permissions.IcdPermissionLevel;
import com.r39.ws.cns.datamodel.doc.permissions.IcdPermissionSubjectType;
import com.ws.ng.mail_client_datamodel.email.EmailEvent;
import com.ws.ng.mail_client_datamodel.header.WsUserHeaders;
import com.ws.ng.mail_client_listener.common.EntitiesReply;
import com.ws.ng.mail_client_listener.common.IcdVertPermissionRow;
import com.ws.ng.mail_client_listener.common.IcdVertPermissionTable;
import com.ws.ng.mail_client_listener.config.MailClientListenerConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
@Log4j2
public class PermissionsProxyService {

    @Autowired
    private MailClientListenerConfig config;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpClient httpClient;

    public void restrictPermissionToEntities(List<EntitiesReply> entitiesReply, EmailEvent emailEvent) {
        entitiesReply.forEach(e -> restrictPermission(e, emailEvent.getHeaders()));
    }

    private void restrictPermission(EntitiesReply entitiesReply, WsUserHeaders userHeaders) {
        var url = config.getPermissionsUrl() + "/vert/entity-permission-table/" + entitiesReply.getEntityId();

        var icdVertPermissionRow = new IcdVertPermissionRow(
                IcdPermissionSubjectType.User, entitiesReply.getUserId(), IcdPermissionLevel.Full.value());

        List<IcdVertPermissionRow> permissionRows = List.of(icdVertPermissionRow);

        var icdVertPermissionTable = new IcdVertPermissionTable();
        icdVertPermissionTable.setRows(permissionRows);

        try {
            HttpRequest httpRequest = HttpRequest
                    .newBuilder()
                    .uri(new URI(url))
                    .header(WsUserHeaders.USER_NAME, userHeaders.getUsername())
                    .header(WsUserHeaders.CLASSIFICATION_VALUE, userHeaders.getClassification())
                    .header(WsUserHeaders.DIVISION_ID, userHeaders.getDivisionId())
                    .header(WsUserHeaders.POSITION_ID, userHeaders.getPositionId())
                    .header(WsUserHeaders.ROLES, userHeaders.getRoles())
                    .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(icdVertPermissionTable)))
                    .build();

            httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            log.info("Vertical permission was created for entity {}", entitiesReply.getEntityId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
