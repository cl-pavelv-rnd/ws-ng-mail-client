package com.ws.ng.mail_client_runner.controller;

import com.ws.ng.mail_client_connector.connector.EmailServerConnector;
import com.ws.ng.mail_client_connector.transformer.EmailMessageTransformer;
import com.ws.ng.mail_client_datamodel.email.IcdEmailMessage;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail-client")
public class EmailController {

    @Autowired
    private EmailServerConnector emailConnector;

    @Autowired
    private EmailMessageTransformer transformer;

    @ApiOperation(
            value = "Fetch email data by id",
            response = IcdEmailMessage.class,
            notes = "This API is for debugging purposes only and will return the email data object directly from the external mail server"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @RequestMapping(value = "/email/{id}", method = RequestMethod.GET)
    public IcdEmailMessage getEmailMessageById(
            @ApiParam(value = "The email id to query by")
            @PathVariable String id) throws Exception {
        return transformer.toIcd(emailConnector.getEmailMessageById(id));
    }

}
