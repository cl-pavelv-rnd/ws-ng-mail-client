package com.ws.ng.mail_client_listener.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.r39.ws.cns.datamodel.icd.base.IcdEntity;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EntitiesReply {

    private String userId;
    private String entityId;
    private String entityType;
    private IcdEntity entityBody;
    private int permissionLevel;

    protected EntitiesReply(
            String userId,
            String entityId,
            String entityType,
            IcdEntity entityBody,
            int permissionLevel) {
        this.userId = userId;
        this.entityId = entityId;
        this.entityType = entityType;
        this.entityBody = entityBody;
        this.permissionLevel = permissionLevel;
    }

    @JsonCreator
    public static EntitiesReply build(
            @JsonProperty("userId") String userId,
            @JsonProperty("entityId") String entityId,
            @JsonProperty("entityType") String entityType,
            @JsonProperty("entityBody") IcdEntity entityBody,
            @JsonProperty("permissionLevel") int permissionLevel) {
        return new EntitiesReply(userId, entityId, entityType, entityBody, permissionLevel);
    }

}