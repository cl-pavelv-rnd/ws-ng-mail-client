package com.ws.ng.mail_client_listener.config;

import com.ws.ng.mail_client_connector.connector.ClientProperties;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Data
public class MailClientListenerConfig {

    @Value("${spring.application.name}")
    private String app;

    @Value("${mail-client-listener.imap.protocol}")
    private String protocol;

    @Value("${mail-client-listener.imap.email-host}")
    private String host;

    @Value("${mail-client-listener.imap.email-port}")
    private String port;

    @Value("${mail-client-listener.imap.user}")
    private String user;

    @Value("${mail-client-listener.imap.password}")
    private String password;

    @Value("${mail-client-listener.imap.folder-name}")
    private String folderName;

    @Value("${mail-client-listener.ews.token-provider-url}")
    private String tokenProviderUrl;

    @Value("${mail-client-listener.ews.ews-url}")
    private String ewsUrl;

    @Value("${mail-client-listener.kafka.input-topic.name}")
    private String inputTopic;

    @Value("${mail-client-listener.kafka.input-topic.partitions}")
    private Integer topicPartitions;

    @Value("${mail-client-listener.kafka.input-topic.replicas}")
    private Short topicReplicas;

    @Value("${services.entities.url}")
    private String entitiesUrl;

    @Value("${services.content-management.url}")
    private String contentManagementUrl;

    @Value("${services.links.url}")
    private String linksUrl;

    @Value("${services.permissions.url}")
    private String permissionsUrl;

    @Bean
    @Profile("!bina-prod")
    public ClientProperties getIMAPClientProperties() {
        var properties = new ClientProperties();
        properties.put("host", host);
        properties.put("port", port);
        properties.put("user", user);
        properties.put("password", password);
        properties.put("protocol", protocol);
        properties.put("folderName", folderName);
        return properties;
    }

    @Bean
    @Profile("bina-prod")
    public ClientProperties getEWSClientProperties() {
        var properties = new ClientProperties();
        properties.put("tokenProviderUrl", tokenProviderUrl);
        properties.put("ewsUrl", ewsUrl);
        return properties;
    }

}
