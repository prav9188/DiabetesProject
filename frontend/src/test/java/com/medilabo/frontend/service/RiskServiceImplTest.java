package com.medilabo.frontend.service;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RiskServiceImplTest {

    private MockWebServer mockWebServer;
    private RiskServiceImpl riskService;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        WebClient.Builder webClientBuilder = WebClient.builder();
        riskService = new RiskServiceImpl(webClientBuilder);

        ReflectionTestUtils.setField(riskService, "gatewayUrl", mockWebServer.url("/").toString());
        ReflectionTestUtils.setField(riskService, "username", "user");
        ReflectionTestUtils.setField(riskService, "password", "pass");
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void testGetRiskByPatientId() {
        mockWebServer.enqueue(new MockResponse()
                .setBody("Borderline")
                .addHeader("Content-Type", "text/plain"));

        String risk = riskService.getRiskByPatientId(1L);

        assertEquals("Borderline", risk);
    }
}
