package com.github.appiumtestdistribution.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.appiumtestdistribution.error.AppiumServiceNotFoundException;
import com.github.appiumtestdistribution.modal.AppiumServerRequest;
import com.github.appiumtestdistribution.modal.AppiumServerResponse;
import com.github.appiumtestdistribution.service.AppiumService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AppiumControllerTest {
    @Mock
    AppiumService service;
    @InjectMocks
    AppiumController target;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Nested
    class UnitTests {
        @Nested
        class startAppiumServer {
            AppiumServerRequest request = new AppiumServerRequest();

            @Test
            void call_service_startAppiumServer() {
                target.startAppiumServer(request);

                verify(service, times(1)).startAppiumServer(eq(request));
            }

            @Test
            void response_service_startAppiumServer() {
                AppiumServerResponse serverResponse = new AppiumServerResponse();
                serverResponse.setConnectionUrl("TEST-URL");
                serverResponse.setPort(100);
                doReturn(serverResponse).when(service).startAppiumServer(any());

                AppiumServerResponse actual = target.startAppiumServer(request);

                assertEquals(serverResponse, actual);
            }
        }

        @Nested
        class stopAppiumServer {
            AppiumServerRequest request = new AppiumServerRequest();

            @Test
            void call_service_stopAppiumServer() {
                target.stopAppiumServer(request);

                verify(service, times(1)).stopAppiumServer();
            }

            @Test
            void response_service_stopAppiumServer() {
                doReturn("TEST-stop-response").when(service).stopAppiumServer();

                String actual = target.stopAppiumServer(request);

                assertEquals("TEST-stop-response", actual);
            }

            @Test
            void throw_exception_if_service_throw_exception() {
                doThrow(new AppiumServiceNotFoundException()).when(service).stopAppiumServer();

                assertThrows(
                        AppiumServiceNotFoundException.class,
                        () -> target.stopAppiumServer(request)
                );
            }

        }
    }

    @Nested
    class WebTests {
        AppiumServerRequest request = new AppiumServerRequest();

        @Nested
        class startAppiumServer {
            @SneakyThrows
            @Test
            void return_AppiumServerResponse_json() {
                AppiumServerResponse serverResponse = new AppiumServerResponse();
                serverResponse.setConnectionUrl("TEST-URL");
                serverResponse.setPort(100);
                doReturn(serverResponse).when(service).startAppiumServer(any());

                MockMvc mockMvc = MockMvcBuilders
                        .standaloneSetup(target)
                        .build();

                mockMvc
                        .perform(MockMvcRequestBuilders
                                .post("/appium/start")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(request))
                        )
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.connectionUrl").value("TEST-URL"))
                        .andExpect(jsonPath("$.port").value(100))
                ;
            }
        }

        @Nested
        class stopAppiumServer {
            @SneakyThrows
            @Test
            void return_stopAppiumServer_message() {
                doReturn("TEST-stopAppiumServer-response").when(service).stopAppiumServer();

                MockMvc mockMvc = MockMvcBuilders
                        .standaloneSetup(target)
                        .build();

                mockMvc
                        .perform(MockMvcRequestBuilders
                                .post("/appium/stop")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(request))
                        )
                        .andExpect(status().isOk())
                        .andExpect(content().string("TEST-stopAppiumServer-response"))
                ;
            }
        }
    }
}