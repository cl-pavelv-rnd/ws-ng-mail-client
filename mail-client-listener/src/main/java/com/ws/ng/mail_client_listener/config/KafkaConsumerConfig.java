package com.ws.ng.mail_client_listener.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConsumerConfig {

    @Autowired
    private MailClientListenerConfig config;

    @Bean
    public NewTopic externalEmailMessagesTopic() {
        return new NewTopic(config.getInputTopic(), config.getTopicPartitions(), config.getTopicReplicas());
    }

}
