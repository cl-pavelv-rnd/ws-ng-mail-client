package com.ws.ng.mail_client_datamodel.header;

import lombok.Getter;

@Getter
public class WsUserHeaders {

    public static final String USER_NAME = "WS-USER-INFO.NAME";
    public static final String CLASSIFICATION_VALUE = "WS-USER-INFO.CLASSIFICATION-VALUE";
    public static final String POSITION_ID = "WS-USER-INFO.POSITION-ID";
    public static final String DIVISION_ID = "WS-USER-INFO.DIVISION-ID";
    public static final String ROLES = "WS-USER-INFO.ROLES";

    private final String username;
    private final String classification;
    private final String positionId;
    private final String divisionId;
    private final String roles;

    public WsUserHeaders() {
        this.username = "WS_SYSTEM";
        this.classification = "1000";
        this.positionId = "WS_SYSTEM_USER_POS";
        this.divisionId = "WS_SYSTEM_USER_DIVISION";
        this.roles = "SUPER-USER";
    }

    public WsUserHeaders(
            String username,
            String classification,
            String positionId,
            String divisionId,
            String roles) {
        this.username = username;
        this.classification = classification;
        this.positionId = positionId;
        this.divisionId = divisionId;
        this.roles = roles;
    }

}
