package com.ws.ng.mail_client_listener.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IcdVertPermissionTable {

    @Valid
    @NotNull
    private List<com.ws.ng.mail_client_listener.common.IcdVertPermissionRow> rows = new ArrayList<>();

}
