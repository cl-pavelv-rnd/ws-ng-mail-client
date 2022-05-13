package com.ws.ng.mail_client_listener.service.proxy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.r39.ws.cns.datamodel.icd.entities.IcdFile;
import com.ws.ng.mail_client_datamodel.email.EmailEvent;
import com.ws.ng.mail_client_datamodel.email.IcdAttachment;
import com.ws.ng.mail_client_datamodel.header.WsUserHeaders;
import com.ws.ng.mail_client_listener.common.EntitiesReply;
import com.ws.ng.mail_client_listener.config.MailClientListenerConfig;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;
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
@Log4j2
public class CMProxyService {

    @Autowired
    private MailClientListenerConfig config;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpClient httpClient;

    public void uploadContentsToCM(EmailEvent emailEvent) throws Exception {
        for (IcdAttachment attachment : emailEvent.getEmailMessage().getAttachments()) {
            uploadContent(attachment, emailEvent.getHeaders());
        }
    }

    private void uploadContent(IcdAttachment attachment, WsUserHeaders userHeaders) throws Exception {
        var url = config.getContentManagementUrl() + "/content";

        HttpRequest httpRequest = HttpRequest
                .newBuilder()
                .uri(new URI(url))
                .header(WsUserHeaders.USER_NAME, userHeaders.getUsername())
                .header(WsUserHeaders.CLASSIFICATION_VALUE, userHeaders.getClassification())
                .header(WsUserHeaders.DIVISION_ID, userHeaders.getDivisionId())
                .header(WsUserHeaders.POSITION_ID, userHeaders.getPositionId())
                .header(WsUserHeaders.ROLES, userHeaders.getRoles())
                .header("X-Content-Length", String.valueOf(attachment.getInputStream().available()))
                .header("X-Extension", getFileExtension(attachment.getName()))
                .header("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .POST(HttpRequest.BodyPublishers.ofInputStream(attachment::getInputStream))
                .build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        String contentId = httpResponse.body();

        attachment.setContentId(contentId);

        log.info("Content id {} was created for file {}", contentId, attachment.getName());
    }

    public List<EntitiesReply> createFileEntitiesInCM(EmailEvent emailEvent) {
        return emailEvent.getEmailMessage().getAttachments()
                .stream()
                .map(a -> createFileEntity(a, emailEvent.getHeaders()))
                .collect(Collectors.toList());
    }

    private EntitiesReply createFileEntity(IcdAttachment attachment, WsUserHeaders userHeaders) throws RuntimeException {
        var url = config.getContentManagementUrl() + "/File/" + attachment.getContentId();

        var icdFile = new IcdFile();
        icdFile.id = attachment.getContentId();
        icdFile.extension = getFileExtension(attachment.getName());
        icdFile.name = FilenameUtils.removeExtension(attachment.getName());
        icdFile.fileType = "file_type_4"; // Document TODO determine file type!

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
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(icdFile)))
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            EntitiesReply entitiesReply = objectMapper.readValue(httpResponse.body(), EntitiesReply.class);

            log.info("File entity {} was created for file {}", entitiesReply.getEntityId(), attachment.getName());

            return entitiesReply;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getFileExtension(String filename) {
        return FilenameUtils.getExtension(filename);
    }

}

