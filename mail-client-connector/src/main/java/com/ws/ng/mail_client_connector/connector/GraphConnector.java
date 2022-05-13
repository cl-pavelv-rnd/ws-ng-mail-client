package com.ws.ng.mail_client_connector.connector;

import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.Message;
import com.microsoft.graph.options.Option;
import com.microsoft.graph.options.QueryOption;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.requests.MessageCollectionPage;
import com.ws.ng.mail_client_connector.message.AbstractMessage;
import com.ws.ng.mail_client_connector.message.graph.GraphMessage;
import com.ws.ng.mail_client_connector.transformer.EmailMessageTransformer;
import com.ws.ng.mail_client_datamodel.email.IcdEmailMessage;
import lombok.extern.log4j.Log4j2;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Component
@Profile("bina-prod")
@Log4j2
public class GraphConnector implements EmailServerConnector {

    @Autowired
    private EmailMessageTransformer transformer;

    private GraphServiceClient<Request> graphClient;

    private String clientId;
    private String clientSecret;
    private String tenantId;
    private String scope;

    @Override
    public void init(Properties properties) {
        this.clientId = properties.getProperty("clientId");
        this.clientSecret = properties.getProperty("clientSecret");
        this.tenantId = properties.getProperty("tenantId");
        this.scope = properties.getProperty("scope");
    }

    @Override
    public void connect() {
        log.info("Connecting to email server...");

        var credential = new ClientSecretCredentialBuilder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .tenantId(tenantId)
                .build();

        List<String> scopes = List.of(scope);

        var authProvider = new TokenCredentialAuthProvider(scopes, credential);

        // build a Graph client
        graphClient = GraphServiceClient
                .builder()
                .authenticationProvider(authProvider)
                .buildClient();

        log.info("Connected!");
    }

    @Override
    public AbstractMessage[] searchEmails(Long from) {
        log.info("Received searchEmails() with from {}", new Date(from));

        var fromDate = new Date(from);
        var toDate = new Date();

        var filterStr = "ReceivedDateTime ge " + fromDate + " and ReceivedDateTime lt " + toDate;

        var filterQuery = new QueryOption("$filter", filterStr);
        var orderByQuery = new QueryOption("$orderBy", "ReceivedDateTime");
        List<Option> requestOptions = List.of(filterQuery, orderByQuery);

        List<Message> allMessages = new ArrayList<>();

        MessageCollectionPage messageCollectionPage = graphClient.me().messages().buildRequest(requestOptions).get();
        while (messageCollectionPage != null && messageCollectionPage.getCount() != null && messageCollectionPage.getCount() != 0) {
            List<Message> currentPage = messageCollectionPage.getCurrentPage();
            allMessages.addAll(currentPage);
        }

        return allMessages.stream().map(GraphMessage::new).toArray(AbstractMessage[]::new);
    }

    @Override
    public AbstractMessage getEmailMessageById(String id) {
        log.info("Received getEmailMessageById() with id {}", id);

        Message message = graphClient.me().messages(id).buildRequest().get();
        return new GraphMessage(message);
    }

    @Override
    public void populateEmailBody(IcdEmailMessage emailMessage) throws Exception {
        log.info("Received populateEmailBody() with id {}", emailMessage.getId());

        AbstractMessage message = getEmailMessageById(emailMessage.getId());
        populateEmailBody(emailMessage, message);
    }

    @Override
    public void close() {

    }

}
