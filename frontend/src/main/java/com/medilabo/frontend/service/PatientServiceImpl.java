package com.medilabo.frontend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class PatientServiceImpl implements PatientService {

    private static final Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);

    private final WebClient webClient;

    @Value("${gateway.url}")
    private String gatewayUrl;

    @Value("${gateway.user}")
    private String username;

    @Value("${gateway.password}")
    private String password;

    public PatientServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    private String createBasicAuthHeader(String username, String password) {
        String auth = username + ":" + password;
        return "Basic " + java.util.Base64.getEncoder().encodeToString(auth.getBytes());
    }

    @Override
    public List<Map> getAllPatients() {
        logger.info("Service layer: Fetching patients list from gateway");
        return webClient.get()
                .uri(gatewayUrl + "/patients")
                .header(HttpHeaders.AUTHORIZATION, createBasicAuthHeader(username, password))
                .retrieve()
                .bodyToFlux(Map.class)
                .collectList()
                .block();
    }

    @Override
    public void addPatient(String firstName, String lastName) {
        logger.info("Service layer: Adding new patient: {} {}", firstName, lastName);
        Map<String, Object> newPatient = Map.of(
                "firstName", firstName,
                "lastName", lastName
        );

        webClient.post()
                .uri(gatewayUrl + "/patients")
                .header(HttpHeaders.AUTHORIZATION, createBasicAuthHeader(username, password))
                .bodyValue(newPatient)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @Override
    public void updatePatient(Long id, String firstName, String lastName) {
        logger.info("Service layer: Updating patient id={}", id);
        Map<String, Object> updatedPatient = Map.of(
                "id", id,
                "firstName", firstName,
                "lastName", lastName
        );

        webClient.put()
                .uri(gatewayUrl + "/patients/" + id)
                .header(HttpHeaders.AUTHORIZATION, createBasicAuthHeader(username, password))
                .bodyValue(updatedPatient)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @Override
    public void deletePatient(Long id) {
        logger.info("Service layer: Deleting patient id={}", id);
        webClient.delete()
                .uri(gatewayUrl + "/patients/" + id)
                .header(HttpHeaders.AUTHORIZATION, createBasicAuthHeader(username, password))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
