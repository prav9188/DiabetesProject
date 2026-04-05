package com.medilabo.frontend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Controller
public class PatientController {

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    private final WebClient webClient;

    @Value("${gateway.url}")
    private String gatewayUrl;

    @Value("${gateway.user}")
    private String username;

    @Value("${gateway.password}")
    private String password;

    public PatientController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @GetMapping("/")
    public String getPatients(Model model) {
        logger.info("Fetching patients list from gateway");
        List<Map> patients = webClient.get()
                .uri(gatewayUrl + "/patients")
                .header(HttpHeaders.AUTHORIZATION, createBasicAuthHeader(username, password))
                .retrieve()
                .bodyToFlux(Map.class)
                .collectList()
                .block();

        model.addAttribute("patients", patients);
        logger.info("Patients list loaded with {} entries", patients != null ? patients.size() : 0);
        return "patients";
    }

    private String createBasicAuthHeader(String username, String password) {
        String auth = username + ":" + password;
        return "Basic " + java.util.Base64.getEncoder().encodeToString(auth.getBytes());
    }

    @PostMapping("/patients/add")
    public String addPatient(@RequestParam String firstName,
                             @RequestParam String lastName) {
        logger.info("Adding new patient: FirstName={}, LastName={}", firstName, lastName);
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

        logger.info("Patient added successfully");
        return "redirect:/";
    }

    @PostMapping("/patients/update")
    public String updatePatient(@RequestParam Long id,
                                @RequestParam String firstName,
                                @RequestParam String lastName) {
        logger.info("Updating patient id={}: FirstName={}, LastName={}", id, firstName, lastName);
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

        logger.info("Patient id={} updated successfully", id);
        return "redirect:/";
    }

    @PostMapping("/patients/delete")
    public String deletePatient(@RequestParam Long id) {
        logger.info("Deleting patient with id={}", id);
        webClient.delete()
                .uri(gatewayUrl + "/patients/" + id)
                .header(HttpHeaders.AUTHORIZATION, createBasicAuthHeader(username, password))
                .retrieve()
                .bodyToMono(Void.class)
                .block();

        logger.info("Patient id={} deleted successfully", id);
        return "redirect:/";
    }
}