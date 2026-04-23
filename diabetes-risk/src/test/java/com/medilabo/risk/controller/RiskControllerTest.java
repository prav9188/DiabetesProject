package com.medilabo.risk.controller;

import com.medilabo.risk.model.NoteDTO;
import com.medilabo.risk.model.PatientDTO;
import com.medilabo.risk.service.RiskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.security.test.context.support.WithMockUser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@WebFluxTest(RiskController.class)
public class RiskControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private RiskController riskController;

    @MockBean
    private RiskService riskService;

    @MockBean
    private WebClient.Builder webClientBuilder;

    private WebClient webClient;
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    private WebClient.RequestHeadersSpec requestHeadersSpec;
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        webClient = Mockito.mock(WebClient.class);
        requestHeadersUriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        requestHeadersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
        responseSpec = Mockito.mock(WebClient.ResponseSpec.class);

        when(webClientBuilder.build()).thenReturn(webClient);

        ReflectionTestUtils.setField(riskController, "webClient", webClient);
        ReflectionTestUtils.setField(riskController, "gatewayUrl", "http://localhost:8080");
        ReflectionTestUtils.setField(riskController, "username", "user");
        ReflectionTestUtils.setField(riskController, "password", "password");
    }

    @Test
    @WithMockUser
    public void testGetRiskByPatientId() {
        Long patientId = 1L;
        PatientDTO patient = new PatientDTO();
        patient.setId(patientId);
        
        NoteDTO note = new NoteDTO();
        note.setContent("Test note");
        List<NoteDTO> notes = Arrays.asList(note);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.header(anyString(), any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        
        // Mocking bodyToMono for PatientDTO
        when(responseSpec.bodyToMono(PatientDTO.class)).thenReturn(Mono.just(patient));
        
        // Mocking bodyToFlux for NoteDTO
        when(responseSpec.bodyToFlux(NoteDTO.class)).thenReturn(Flux.fromIterable(notes));

        when(riskService.calculateRisk(any(), any())).thenReturn("None");

        String result = riskController.getRiskByPatientId(patientId).block();
        assertEquals("None", result);
    }
}
