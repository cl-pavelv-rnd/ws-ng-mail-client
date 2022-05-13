package com.ws.ng.mail_client_runner.controller;

import com.ws.ng.mail_client_runner.service.UsersCacheService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users-cache")
public class UsersCacheController {

    @Autowired
    private UsersCacheService service;

    @ApiOperation(
            value = "Refresh the users cache against UserDirectory service",
            notes = "This API is for debugging purposes only and will refresh the users cache on demand."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @RequestMapping(value = "/refresh", method = RequestMethod.PUT)
    public void refreshUsersCache() throws Exception {
        service.refreshUsersCache();
    }

}
