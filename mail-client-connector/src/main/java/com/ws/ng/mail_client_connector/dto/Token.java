package com.ws.ng.mail_client_connector.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Token {
    @JsonProperty("AccessToken")
    private String accessToken;
}
