package com.ws.ng.mail_client_listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.ws.ng.mail_client_listener",
        "com.ws.ng.mail_client_connector"
})
public class MailClientListenerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailClientListenerApplication.class, args);
    }

}
