package com.ws.ng.mail_client_runner.controller;

import com.ws.ng.mail_client_runner.redis.RedisService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail-client")
public class RedisController {

    @Autowired
    private RedisService service;

    @ApiOperation(
            value = "Reset the Redis lastMessageIndicator cache for the used email address",
            notes = "This API is for debugging purposes only and will set the lastMessageIndicator to given value or 0 (default), "
                    + "meaning that all the messages above this value will be processed again!"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @RequestMapping(value = "/reset", method = RequestMethod.PUT)
    public void resetLastMessageIndicator(
            @ApiParam(value = "The lastMessageIndicator to which to reset")
            @RequestParam(defaultValue = "0") long lastMessageIndicator) {
        service.resetLastMessageIndicator(lastMessageIndicator);
    }

}
