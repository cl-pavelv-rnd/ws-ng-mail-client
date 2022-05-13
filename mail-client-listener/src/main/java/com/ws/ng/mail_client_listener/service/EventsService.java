package com.ws.ng.mail_client_listener.service;

import com.ws.ng.mail_client_datamodel.email.EmailEvent;
import com.ws.ng.mail_client_datamodel.email.IcdEmailMessage;
import com.ws.ng.mail_client_listener.common.EntitiesReply;
import com.ws.ng.mail_client_listener.service.proxy.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
@Log4j2
public class EventsService implements Runnable {

    private static final int N_THREADS = 4;

    private final LinkedBlockingQueue<EmailEvent> eventsQueue = new LinkedBlockingQueue<>();

    @Autowired
    private EmailProxyService emailProxy;

    @Autowired
    private CMProxyService cmProxy;

    @Autowired
    private PermissionsProxyService permissionsProxy;

    @Autowired
    private EntitiesProxyService entitiesProxy;

    @Autowired
    private LinksProxyService linksProxy;

    @PostConstruct
    public void init() {
        ExecutorService executor = Executors.newScheduledThreadPool(N_THREADS);
        for (int i = 0; i < N_THREADS; i++) {
            executor.execute(this);
        }
    }

    public void subscribe(EmailEvent emailEvent) throws InterruptedException {
        eventsQueue.put(emailEvent);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while (true) {
            try {
                process();
            } catch (Exception e) {
                log.error("Error in processing message", e);
            }
        }
    }

    private void process() throws Exception {
        log.info("Trying to take an emailEvent...");

        EmailEvent emailEvent = eventsQueue.take();
        IcdEmailMessage emailMessage = emailEvent.getEmailMessage();

        log.info("==== Processing message {} =====", emailMessage.getId());

        // populate the body of the email message (attachments and content)
        emailProxy.populateEmailBody(emailMessage);
        log.info("Email body was populated");

        // upload the attachments to CM
        cmProxy.uploadContentsToCM(emailEvent);
        log.info("{} attachments were uploaded", emailMessage.getAttachments().size());

        // create the file entities for the attachments
        List<EntitiesReply> fileEntitiesReplies = cmProxy.createFileEntitiesInCM(emailEvent);
        log.info("{} File entities were created", fileEntitiesReplies.size());

        // create the email entity itself
        EntitiesReply emailEntityReply = entitiesProxy.createEmailEntity(emailEvent);
        log.info("Email entity {} was created", emailEntityReply.getEntityId());

        // grant user permissions for the created files and email entity
        List<EntitiesReply> privateEntities = new ArrayList<>(fileEntitiesReplies);
        privateEntities.add(emailEntityReply);
        permissionsProxy.restrictPermissionToEntities(privateEntities, emailEvent);
        log.info("Permissions to {} entities were granted", privateEntities.size());

        // create links between the email and the file entities
        linksProxy.createFileLinks(emailEntityReply, fileEntitiesReplies, emailEvent);
        log.info("Links to {} file entities were created", fileEntitiesReplies.size());

        log.info("===== Message {} was processed successfully =====", emailEvent.getEmailMessage().getId());
    }

}
