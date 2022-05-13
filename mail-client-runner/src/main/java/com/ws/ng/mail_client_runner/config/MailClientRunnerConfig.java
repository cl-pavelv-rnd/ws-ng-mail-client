package com.ws.ng.mail_client_runner.config;

import com.ws.ng.mail_client_connector.connector.ClientProperties;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Set;

@Configuration
@Data
public class MailClientRunnerConfig {

    @Value("${spring.application.name}")
    private String app;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Value("${spring.kafka.properties.schema.registry.url}")
    private String schemaRegistryUrl;

    @Value("${mail-client-runner.emails-pull-delay}")
    private Integer emailsPullDelay;

    @Value("${mail-client-runner.users-pull-delay}")
    private Integer usersPullDelay;

    @Value("${mail-client-runner.imap.protocol}")
    private String protocol;

    @Value("${mail-client-runner.imap.email-host}")
    private String host;

    @Value("${mail-client-runner.imap.email-port}")
    private String port;

    @Value("${mail-client-runner.imap.user}")
    private String user;

    @Value("${mail-client-runner.imap.password}")
    private String password;

    @Value("${mail-client-runner.imap.folder-name}")
    private String folderName;

    @Value("${mail-client-runner.ews.token-provider-url}")
    private String tokenProviderUrl;

    @Value("${mail-client-runner.ews.ews-url}")
    private String ewsUrl;

    @Value("#{'${mail-client-runner.user-validation.domain.whitelist}'.split(',')}")
    private Set<String> allowedDomains;

    @Value("${mail-client-runner.kafka.output-topic.name}")
    private String outputTopic;

    @Value("${mail-client-runner.kafka.output-topic.partitions}")
    private Integer topicPartitions;

    @Value("${mail-client-runner.kafka.output-topic.replicas}")
    private Short topicReplicas;

    @Value("${services.user-dir.url}")
    private String userDirectoryUrl;

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
