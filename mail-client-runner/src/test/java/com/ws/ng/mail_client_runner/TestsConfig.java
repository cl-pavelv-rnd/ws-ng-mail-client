package com.ws.ng.mail_client_runner;

import com.ws.ng.mail_client_avro.ExternalEmailMessageEvent;
import com.ws.ng.mail_client_connector.connector.EmailServerConnector;
import com.ws.ng.mail_client_runner.config.KafkaProducerConfig;
import com.ws.ng.mail_client_runner.runner.MailClientRunner;
import com.ws.ng.mail_client_runner.runner.UsersCacheRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@ComponentScan({
        "com.ws.ng.mail_client_runner"
})
@Configuration
public class TestsConfig {

    @MockBean
    private UsersCacheRunner usersCacheRunner;

    @MockBean
    private MailClientRunner mailClientRunner;

    @MockBean
    private EmailServerConnector emailServerConnector;

    @MockBean
    private KafkaProducerConfig kafkaProducer;

    @MockBean
    private KafkaTemplate<String, ExternalEmailMessageEvent> kafkaTemplate;

}
