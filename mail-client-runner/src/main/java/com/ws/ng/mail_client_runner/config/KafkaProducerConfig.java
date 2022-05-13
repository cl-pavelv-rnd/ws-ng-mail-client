package com.ws.ng.mail_client_runner.config;

import com.ws.ng.mail_client_avro.ExternalEmailMessageEvent;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;

@Configuration
public class KafkaProducerConfig {

    @Autowired
    private MailClientRunnerConfig config;

    @Bean
    public NewTopic externalEmailMessagesTopic() {
        return new NewTopic(config.getOutputTopic(), config.getTopicPartitions(), config.getTopicReplicas());
    }

    @Bean(name = "producerFactory_ExternalEmailMessageEvent")
    public ProducerFactory<String, ExternalEmailMessageEvent> producerFactory() {
        var producerConfig = new HashMap<String, Object>();
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServer());
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        producerConfig.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, config.getSchemaRegistryUrl());
        return new DefaultKafkaProducerFactory<>(producerConfig);
    }

    @Bean(name = "kafkaTemplate_ExternalEmailMessageEvent")
    public KafkaTemplate<String, ExternalEmailMessageEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}
