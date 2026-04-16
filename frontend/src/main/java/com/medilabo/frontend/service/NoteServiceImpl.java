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
public class NoteServiceImpl implements NoteService {

    private static final Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

    private final WebClient webClient;

    @Value("${gateway.url}")
    private String gatewayUrl;

    @Value("${gateway.user}")
    private String username;

    @Value("${gateway.password}")
    private String password;

    public NoteServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    private String createBasicAuthHeader(String username, String password) {
        String auth = username + ":" + password;
        return "Basic " + java.util.Base64.getEncoder().encodeToString(auth.getBytes());
    }

    @Override
    public List<Map> getNotesByPatientId(Long patientId) {
        logger.info("Service layer: Fetching notes for patient id={} from gateway", patientId);
        return webClient.get()
                .uri(gatewayUrl + "/notes/patient/" + patientId)
                .header(HttpHeaders.AUTHORIZATION, createBasicAuthHeader(username, password))
                .retrieve()
                .bodyToFlux(Map.class)
                .collectList()
                .block();
    }

    @Override
    public void addNote(Long patientId, String content) {
        logger.info("Service layer: Adding new note for patient id={}", patientId);
        Map<String, Object> newNote = Map.of(
                "patientId", patientId,
                "content", content
        );

        webClient.post()
                .uri(gatewayUrl + "/notes")
                .header(HttpHeaders.AUTHORIZATION, createBasicAuthHeader(username, password))
                .bodyValue(newNote)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @Override
    public void updateNote(String id, String content) {
        logger.info("Service layer: Updating note id={}", id);
        Map<String, Object> updatedNote = Map.of(
                "id", id,
                "content", content
        );

        webClient.put()
                .uri(gatewayUrl + "/notes/" + id)
                .header(HttpHeaders.AUTHORIZATION, createBasicAuthHeader(username, password))
                .bodyValue(updatedNote)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @Override
    public void deleteNote(String id) {
        logger.info("Service layer: Deleting note id={}", id);
        webClient.delete()
                .uri(gatewayUrl + "/notes/" + id)
                .header(HttpHeaders.AUTHORIZATION, createBasicAuthHeader(username, password))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
