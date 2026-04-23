package com.medilabo.frontend.service;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PatientServiceImplTest {

    private MockWebServer mockWebServer;
    private PatientServiceImpl patientService;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        WebClient.Builder webClientBuilder = WebClient.builder();
        patientService = new PatientServiceImpl(webClientBuilder);

        ReflectionTestUtils.setField(patientService, "gatewayUrl", mockWebServer.url("/").toString());
        ReflectionTestUtils.setField(patientService, "username", "user");
        ReflectionTestUtils.setField(patientService, "password", "pass");
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void testGetAllPatients() {
        mockWebServer.enqueue(new MockResponse()
                .setBody("[{\"id\": 1, \"firstName\": \"John\"}]")
                .addHeader("Content-Type", "application/json"));

        List<Map> patients = patientService.getAllPatients();

        assertNotNull(patients);
        assertEquals(1, patients.size());
        assertEquals(1, patients.get(0).get("id"));
    }

    @Test
    void testAddPatient() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200));

        patientService.addPatient("John", "Doe", "john@doe.com", "1980-01-01", "M", "123", "Address");

        assertEquals(1, mockWebServer.getRequestCount());
    }

    @Test
    void testUpdatePatient() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200));

        patientService.updatePatient(1L, "John", "Doe", "john@doe.com", "1980-01-01", "M", "123", "Address");

        assertEquals(1, mockWebServer.getRequestCount());
    }

    @Test
    void testDeletePatient() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200));

        patientService.deletePatient(1L);

        assertEquals(1, mockWebServer.getRequestCount());
    }
}
