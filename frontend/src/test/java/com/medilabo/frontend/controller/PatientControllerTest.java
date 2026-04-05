package com.medilabo.frontend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.ui.Model;
import org.springframework.test.util.ReflectionTestUtils;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.web.util.UriBuilder;
import java.util.function.Function;

@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PatientController patientController;

    @MockBean
    private WebClient.Builder webClientBuilder;

    private WebClient webClient;
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;
    private WebClient.RequestHeadersSpec requestHeadersSpec;
    private WebClient.RequestBodySpec requestBodySpec;
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setup() {
        webClient = Mockito.mock(WebClient.class);
        requestHeadersUriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        requestBodyUriSpec = Mockito.mock(WebClient.RequestBodyUriSpec.class);
        requestHeadersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
        requestBodySpec = Mockito.mock(WebClient.RequestBodySpec.class);
        responseSpec = Mockito.mock(WebClient.ResponseSpec.class);

        // Inject the mocked WebClient into the already created controller
        ReflectionTestUtils.setField(patientController, "webClient", webClient);
        // Also inject some dummy values for the @Value fields if they are not picked up
        ReflectionTestUtils.setField(patientController, "gatewayUrl", "http://test-gateway");
        ReflectionTestUtils.setField(patientController, "username", "testuser");
        ReflectionTestUtils.setField(patientController, "password", "testpass");

        when(webClientBuilder.build()).thenReturn(webClient);
    }

    @Test
    void testGetPatients() throws Exception {
        // Mock the WebClient call chain
        doReturn(requestHeadersUriSpec).when(webClient).get();
        doReturn(requestHeadersSpec).when(requestHeadersUriSpec).uri(anyString());
        doReturn(requestHeadersSpec).when(requestHeadersSpec).header(eq(HttpHeaders.AUTHORIZATION), anyString());
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();

        // Mock list of patients returned
        List<Map<String, Object>> mockPatients = List.of(
                Map.of("id", 1, "firstName", "Alice", "lastName", "Smith"),
                Map.of("id", 2, "firstName", "Bob", "lastName", "Brown")
        );
        when(responseSpec.bodyToFlux(Map.class)).thenReturn(Flux.fromIterable(mockPatients));

        // Perform GET /
        MvcResult mvcResult = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients"))
                .andExpect(model().attributeExists("patients"))
                .andReturn();

        @SuppressWarnings("unchecked")
        List<Map> patients = (List<Map>) mvcResult.getModelAndView().getModel().get("patients");
        // Assert the model attribute has expected size
        assert(patients.size() == 2);

    }

    @Test
    void testAddPatient() throws Exception {
        doReturn(requestBodyUriSpec).when(webClient).post();
        doReturn(requestBodySpec).when(requestBodyUriSpec).uri(contains("/patients"));
        doReturn(requestBodySpec).when(requestBodySpec).header(eq(HttpHeaders.AUTHORIZATION), anyString());
        doReturn(requestBodySpec).when(requestBodySpec).bodyValue(any());
        doReturn(responseSpec).when(requestBodySpec).retrieve();
        when(responseSpec.bodyToMono(Void.class)).thenReturn(Mono.empty());

        mockMvc.perform(post("/patients/add")
                        .param("firstName", "John")
                        .param("lastName", "Doe"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void testUpdatePatient() throws Exception {
        doReturn(requestBodyUriSpec).when(webClient).put();
        doReturn(requestBodySpec).when(requestBodyUriSpec).uri(startsWith("http"));
        doReturn(requestBodySpec).when(requestBodySpec).header(eq(HttpHeaders.AUTHORIZATION), anyString());
        doReturn(requestBodySpec).when(requestBodySpec).bodyValue(any());
        doReturn(responseSpec).when(requestBodySpec).retrieve();
        when(responseSpec.bodyToMono(Void.class)).thenReturn(Mono.empty());

        mockMvc.perform(post("/patients/update")
                        .param("id", "1")
                        .param("firstName", "Jane")
                        .param("lastName", "Doe"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void testDeletePatient() throws Exception {
        doReturn(requestHeadersUriSpec).when(webClient).delete();
        doReturn(requestHeadersSpec).when(requestHeadersUriSpec).uri(startsWith("http"));
        doReturn(requestHeadersSpec).when(requestHeadersSpec).header(eq(HttpHeaders.AUTHORIZATION), anyString());
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
        when(responseSpec.bodyToMono(Void.class)).thenReturn(Mono.empty());

        mockMvc.perform(post("/patients/delete")
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
