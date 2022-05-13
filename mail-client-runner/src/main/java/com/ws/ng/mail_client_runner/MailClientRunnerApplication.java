package com.ws.ng.mail_client_runner;

import com.ws.ng.loginfra.web.LoggingRestConfig;
import com.ws.ng.loginfra.web.RestClientLogConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@ComponentScan(basePackages = {
        "com.ws.ng.mail_client_runner",
        "com.ws.ng.mail_client_connector"
})
@EnableRedisRepositories(basePackages = {
        "com.ws.ng.mail_client_runner.repository"
})
@SpringBootApplication
@Import({
    LoggingRestConfig.class,
    RestClientLogConfig.class
})
public class MailClientRunnerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailClientRunnerApplication.class, args);
    }

}
