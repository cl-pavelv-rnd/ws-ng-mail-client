package com.ws.ng.mail_client_listener.common;

import com.r39.ws.cns.datamodel.doc.permissions.IcdPermissionSubjectType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IcdVertPermissionRow implements Cloneable {

    @NotNull
    private IcdPermissionSubjectType subjectType;

    @NotNull
    private String subjectId;

    @NotNull
    private int permissionLevel;

    @Override
    public com.ws.ng.mail_client_listener.common.IcdVertPermissionRow clone() {
        try {
            return (com.ws.ng.mail_client_listener.common.IcdVertPermissionRow) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone not supported", e);
        }
    }

}
