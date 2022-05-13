package com.ws.ng.mail_client_runner.runner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.r39.ws.cns.datamodel.icd.userDirectory.IcdUser;
import com.ws.ng.mail_client_datamodel.header.WsUserHeaders;
import com.ws.ng.mail_client_runner.common.GetAllUsersRequest;
import com.ws.ng.mail_client_runner.common.GetAllUsersResponse;
import com.ws.ng.mail_client_runner.config.MailClientRunnerConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This runner is responsible for loading the users cache periodically.
 */
@Component
@Log4j2
public class UsersCacheRunner extends ConcurrentHashMap<String, IcdUser> implements Runnable {

    @Autowired
    private MailClientRunnerConfig config;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpClient httpClient;

    @PostConstruct
    public void init() {
        log.info("UsersCacheRunner started...");

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleWithFixedDelay(this, 0, config.getUsersPullDelay(), TimeUnit.MINUTES);
    }

    @Override
    public void run() {
        try {
            loadUsers();
        } catch (Exception e) {
            log.error("Failed to load users cache", e);
            System.exit(1);
        }
    }

    public void loadUsers() throws Exception {
        var url = config.getUserDirectoryUrl() + "/userDirectory/GetAllUsersRequest";

        GetAllUsersRequest body = GetAllUsersRequest
                .builder()
                .pageSize(10000)
                .build();

        var systemHeaders = new WsUserHeaders();

        HttpRequest httpRequest = HttpRequest
                .newBuilder()
                .uri(new URI(url))
                .header(WsUserHeaders.USER_NAME, systemHeaders.getUsername())
                .header(WsUserHeaders.CLASSIFICATION_VALUE, systemHeaders.getClassification())
                .header(WsUserHeaders.DIVISION_ID, systemHeaders.getDivisionId())
                .header(WsUserHeaders.POSITION_ID, systemHeaders.getPositionId())
                .header(WsUserHeaders.ROLES, systemHeaders.getRoles())
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(body)))
                .build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        GetAllUsersResponse getAllUsersResponse = objectMapper.readValue(httpResponse.body(), GetAllUsersResponse.class);

        if (getAllUsersResponse != null) {
            Set<IcdUser> users = getAllUsersResponse.getUsers();

            log.info("Fetched {} users from UserDirectory service", users.size());

            Map<String, IcdUser> usersData = users
                    .stream()
                    .collect(Collectors.toMap(IcdUser::getUserName, Function.identity()));

            this.putAll(usersData);
        }

        if (this.size() == 0) {
            throw new RuntimeException("Users cache not loaded yet...");
        }
    }

}
