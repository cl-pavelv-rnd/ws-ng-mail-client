package com.ws.ng.mail_client_connector.helper;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDomainHelper {

    public UserDomain getUserDomain(String emailAddress) {
        String user = Optional.ofNullable(emailAddress)
                .filter(e -> e.contains("@"))
                .map(e -> e.substring(0, e.lastIndexOf("@")))
                .orElse(null);
        String domain = Optional.ofNullable(emailAddress)
                .filter(e -> e.contains("@"))
                .map(e -> e.substring(e.lastIndexOf("@") + 1))
                .orElse(null);

        return UserDomain.builder()
                .user(user)
                .domain(domain)
                .build();
    }

    @Data
    @Builder
    public static class UserDomain {
        private String user;
        private String domain;
    }

}
