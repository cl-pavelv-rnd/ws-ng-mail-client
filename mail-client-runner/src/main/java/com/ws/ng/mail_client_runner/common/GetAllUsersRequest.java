package com.ws.ng.mail_client_runner.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class GetAllUsersRequest {

    private int pageSize;

}
