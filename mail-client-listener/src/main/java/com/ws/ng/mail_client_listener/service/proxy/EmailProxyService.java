package com.ws.ng.mail_client_listener.service.proxy;

import com.ws.ng.mail_client_connector.connector.ClientProperties;
import com.ws.ng.mail_client_connector.connector.EmailServerConnector;
import com.ws.ng.mail_client_datamodel.email.IcdEmailMessage;
import com.ws.ng.mail_client_listener.config.MailClientListenerConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Log4j2
public class EmailProxyService {

    @Autowired
    private MailClientListenerConfig config;

    @Autowired
    private ClientProperties clientProperties;

    @Autowired
    private EmailServerConnector emailConnector;

    @PostConstruct
    public void init() throws Exception {
        emailConnector.init(clientProperties);
        emailConnector.connect();
    }

    /**
     * This method connects to the external email server and fetches the email body: attachments and content.
     * Getting the body may take some time..
     */
    public void populateEmailBody(IcdEmailMessage emailMessage) throws Exception {
        log.info("Getting email body for email {}", emailMessage.getId());

        emailConnector.populateEmailBody(emailMessage);
    }

}
