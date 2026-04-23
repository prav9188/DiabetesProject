package com.medilabo.risk.controller;

import com.medilabo.risk.model.NoteDTO;
import com.medilabo.risk.model.PatientDTO;
import com.medilabo.risk.service.RiskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Controller providing the risk assessment endpoint.
 * It coordinates with the Gateway to aggregate data from Patient and Note services
 * before calling the calculation logic in RiskService.
 */
@RestController
@RequestMapping("/risk")
public class RiskController {

    private static final Logger logger = LoggerFactory.getLogger(RiskController.class);

    private final RiskService riskService;
    private final WebClient webClient;

    @Value("${gateway.url}")
    private String gatewayUrl;

    @Value("${gateway.user}")
    private String username;

    @Value("${gateway.password}")
    private String password;

    public RiskController(RiskService riskService, WebClient.Builder webClientBuilder) {
        this.riskService = riskService;
        this.webClient = webClientBuilder.build();
    }

    private String createBasicAuthHeader() {
        String auth = username + ":" + password;
        return "Basic " + java.util.Base64.getEncoder().encodeToString(auth.getBytes());
    }

    /**
     * Retrieves the diabetes risk level for a specific patient.
     * Uses reactive programming (WebClient) to fetch data asynchronously from multiple sources.
     *
     * @param patientId The unique ID of the patient to evaluate.
     * @return A Mono emitting the calculated risk level.
     */
    @GetMapping("/{patientId}")
    public Mono<String> getRiskByPatientId(@PathVariable Long patientId) {
        logger.info("Request for risk assessment for patient id={}", patientId);

        Mono<PatientDTO> patientMono = webClient.get()
                .uri(gatewayUrl + "/patients/" + patientId)
                .header(HttpHeaders.AUTHORIZATION, createBasicAuthHeader())
                .retrieve()
                .bodyToMono(PatientDTO.class);

        Mono<List<NoteDTO>> notesMono = webClient.get()
                .uri(gatewayUrl + "/notes/patient/" + patientId)
                .header(HttpHeaders.AUTHORIZATION, createBasicAuthHeader())
                .retrieve()
                .bodyToFlux(NoteDTO.class)
                .collectList();

        return Mono.zip(patientMono, notesMono)
                .map(tuple -> riskService.calculateRisk(tuple.getT1(), tuple.getT2()));
    }
}