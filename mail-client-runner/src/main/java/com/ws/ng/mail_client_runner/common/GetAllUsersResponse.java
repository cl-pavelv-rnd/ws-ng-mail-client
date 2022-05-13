package com.ws.ng.mail_client_runner.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.r39.ws.cns.datamodel.icd.userDirectory.IcdUser;
import lombok.Data;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class GetAllUsersResponse {

    private Set<IcdUser> users;

}
