package com.medilabo.frontend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class RiskServiceImpl implements RiskService {

    private static final Logger logger = LoggerFactory.getLogger(RiskServiceImpl.class);

    private final WebClient webClient;

    @Value("${gateway.url}")
    private String gatewayUrl;

    @Value("${gateway.user}")
    private String username;

    @Value("${gateway.password}")
    private String password;

    public RiskServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    private String createBasicAuthHeader() {
        String auth = username + ":" + password;
        return "Basic " + java.util.Base64.getEncoder().encodeToString(auth.getBytes());
    }

    @Override
    public String getRiskByPatientId(Long patientId) {
        logger.info("Fetching risk for patient id={} from gateway", patientId);
        return webClient.get()
                .uri(gatewayUrl + "/risk/" + patientId)
                .header(HttpHeaders.AUTHORIZATION, createBasicAuthHeader())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}