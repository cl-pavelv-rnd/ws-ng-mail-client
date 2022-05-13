package com.ws.ng.mail_client_runner.runner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.r39.ws.cns.datamodel.icd.userDirectory.IcdUser;
import com.ws.ng.mail_client_runner.common.GetAllUsersRequest;
import com.ws.ng.mail_client_runner.common.GetAllUsersResponse;
import com.ws.ng.mail_client_runner.config.MailClientRunnerConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class UsersCacheRunnerTest {

    @InjectMocks
    @Autowired
    private UsersCacheRunner usersCacheRunner;

    @Mock
    private MailClientRunnerConfig config;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse<Object> httpResponse;

    private static GetAllUsersRequest getAllUsersRequest;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    static void start() {
        getAllUsersRequest = GetAllUsersRequest
                .builder()
                .pageSize(10000)
                .build();
    }

    @Test
    public void testLoadUsers_usersLoaded_cachePopulated() throws Exception {
        IcdUser icdUser = new IcdUser();
        icdUser.setUserName("wstu1");

        Set<IcdUser> users = Set.of(icdUser);

        GetAllUsersResponse getAllUsersResponse = new GetAllUsersResponse();
        getAllUsersResponse.setUsers(users);

        when(config.getUserDirectoryUrl()).thenReturn("http://localhost:12015");
        when(objectMapper.writeValueAsString(any())).thenReturn(new ObjectMapper().writeValueAsString(getAllUsersRequest));
        when(httpClient.send(any(), any())).thenReturn(httpResponse);
        when(httpResponse.body()).thenReturn(new ObjectMapper().writeValueAsString(getAllUsersResponse));
        when(objectMapper.readValue(anyString(), eq(GetAllUsersResponse.class))).thenReturn(getAllUsersResponse);

        usersCacheRunner.loadUsers();

        IcdUser existingUser = usersCacheRunner.get("wstu1");
        IcdUser notExistingUser = usersCacheRunner.get("wstu2");

        assertNotNull(existingUser);
        assertNull(notExistingUser);

        verify(config, times(1)).getUserDirectoryUrl();
        verify(objectMapper, times(1)).writeValueAsString(any());
        verify(httpClient, times(1)).send(any(), any());
        verify(httpResponse, times(1)).body();
        verify(objectMapper, times(1)).readValue(anyString(), eq(GetAllUsersResponse.class));
    }

    @Test
    public void testLoadUsers_getUsersEmpty_exceptionThrown() throws Exception {
        GetAllUsersResponse getAllUsersResponse = new GetAllUsersResponse();
        getAllUsersResponse.setUsers(new HashSet<>());

        when(config.getUserDirectoryUrl()).thenReturn("http://localhost:12015");
        when(objectMapper.writeValueAsString(any())).thenReturn(new ObjectMapper().writeValueAsString(getAllUsersRequest));
        when(httpClient.send(any(), any())).thenReturn(httpResponse);
        when(httpResponse.body()).thenReturn(new ObjectMapper().writeValueAsString(getAllUsersResponse));
        when(objectMapper.readValue(anyString(), eq(GetAllUsersResponse.class))).thenReturn(getAllUsersResponse);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> usersCacheRunner.loadUsers());
        assertEquals("Users cache not loaded yet...", thrown.getMessage());

        verify(config, times(1)).getUserDirectoryUrl();
        verify(objectMapper, times(1)).writeValueAsString(any());
        verify(httpClient, times(1)).send(any(), any());
        verify(httpResponse, times(1)).body();
        verify(objectMapper, times(1)).readValue(anyString(), eq(GetAllUsersResponse.class));
    }

}
