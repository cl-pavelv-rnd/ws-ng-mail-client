package com.ws.ng.mail_client_connector.connector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ws.ng.mail_client_connector.dto.Token;
import com.ws.ng.mail_client_connector.message.AbstractMessage;
import com.ws.ng.mail_client_connector.message.ews.EWSMessage;
import com.ws.ng.mail_client_connector.service.ExchangeServiceNoCertValidation;
import com.ws.ng.mail_client_datamodel.email.IcdEmailMessage;
import lombok.extern.log4j.Log4j2;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.enumeration.search.LogicalOperator;
import microsoft.exchange.webservices.data.core.enumeration.search.SortDirection;
import microsoft.exchange.webservices.data.core.exception.service.local.ServiceLocalException;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.core.service.schema.ItemSchema;
import microsoft.exchange.webservices.data.property.complex.ItemId;
import microsoft.exchange.webservices.data.search.ItemView;
import microsoft.exchange.webservices.data.search.filter.SearchFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.*;

@Component
@Profile("bina-prod")
@Log4j2
public class EWSConnector implements EmailServerConnector {

    private final ExchangeService exchangeService = new ExchangeServiceNoCertValidation();

    @Autowired
    private HttpClient httpClient;

    @Autowired
    private ObjectMapper mapper;

    private String tokenProviderUrl;
    private String ewsUrl;

    @Override
    public void init(Properties properties) {
        this.tokenProviderUrl = properties.getProperty("tokenProviderUrl");
        this.ewsUrl = properties.getProperty("ewsUrl");
    }

    @Override
    public void connect() throws Exception {
        log.info("Connecting to email server...");

        HttpRequest httpRequest = HttpRequest
                .newBuilder()
                .uri(new URI(tokenProviderUrl))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Token token = mapper.readValue(response.body(), Token.class);

        exchangeService.setUrl(URI.create(ewsUrl));
        exchangeService.getHttpHeaders().put("Authorization", "Bearer " + token.getAccessToken());

        log.info("Connected!");
    }

    @Override
    public AbstractMessage[] searchEmails(Long from) throws Exception {
        log.info("Received searchEmails() with from {}", new Date(from));

        var pageSize = 100;
        var offset = 0;

        SearchFilter inRangeFilter = createSearchFilter(from);
        ItemView itemView = createItemView(pageSize, offset);

        List<Item> allMessages = new ArrayList<>();

        List<Item> itemsPage = getItemsPage(inRangeFilter, itemView);

        while (itemsPage.size() == pageSize) {
            allMessages.addAll(itemsPage);

            offset += pageSize;
            itemView.setOffset(offset);

            itemsPage = getItemsPage(inRangeFilter, itemView);
        }

        allMessages.addAll(itemsPage);

        return allMessages
                .stream()
                .filter(x -> x instanceof EmailMessage)
                .map(x -> (EmailMessage) x)
                .map(EWSMessage::new)
                .toArray(AbstractMessage[]::new);
    }

    @Override
    public AbstractMessage getEmailMessageById(String id) throws Exception {
        Item item = exchangeService.bindToItem(ItemId.getItemIdFromString(id), PropertySet.FirstClassProperties);
        if (item instanceof EmailMessage) {
            EmailMessage emailMessage = (EmailMessage) item;
            return new EWSMessage(emailMessage);
        }
        throw new Exception("This item isn't instance of email");
    }

    @Override
    public void populateEmailBody(IcdEmailMessage emailMessage) throws Exception {
        AbstractMessage message = getEmailMessageById(emailMessage.getId());
        populateEmailBody(emailMessage, message);
    }

    @Override
    public void close() {
        exchangeService.close();
    }

    private SearchFilter createSearchFilter(Long from) {
        SearchFilter fromFilter = new SearchFilter.IsGreaterThanOrEqualTo(ItemSchema.DateTimeReceived, new Date(from));
        SearchFilter toFilter = new SearchFilter.IsLessThan(ItemSchema.DateTimeReceived, Date.from(Instant.now()));
        return new SearchFilter.SearchFilterCollection(LogicalOperator.And, fromFilter, toFilter);
    }

    private ItemView createItemView(int pageSize, int offset) throws ServiceLocalException {
        var itemView = new ItemView(pageSize, offset);
        itemView.getOrderBy().add(ItemSchema.DateTimeReceived, SortDirection.Ascending);
        return itemView;
    }

    private List<Item> getItemsPage(SearchFilter inRangeFilter, ItemView itemView) throws Exception {
        return exchangeService.findItems(WellKnownFolderName.Inbox, inRangeFilter, itemView).getItems();
    }

}
