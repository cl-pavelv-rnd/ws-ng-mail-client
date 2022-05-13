package com.ws.ng.mail_client_runner.service;

import com.ws.ng.mail_client_runner.runner.UsersCacheRunner;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This service is responsible for refreshing the users cache from UserDirectory service.
 */
@Service
@Log4j2
public class UsersCacheService {

    @Autowired
    private UsersCacheRunner usersCache;

    public void refreshUsersCache() throws Exception {
        log.info("Received refreshUsersCache()");
        usersCache.loadUsers();
    }

}
