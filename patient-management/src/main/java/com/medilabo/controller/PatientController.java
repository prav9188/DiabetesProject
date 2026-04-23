package com.medilabo.controller;

import com.medilabo.entity.Patient;
import com.medilabo.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller managing patient resource operations.
 * Provides RESTful endpoints for CRUD operations on patient records.
 * Uses a relational database for persistent storage.
 */
@RestController
@RequestMapping("/patients")
public class PatientController {

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * Retrieves all registered patients.
     *
     * @return A list of all patients in the system.
     */
    @GetMapping
    public List<Patient> getAllPatients() {
        logger.info("Request to get all patients");
        return patientService.getAllPatients();
    }

    /**
     * Registers a new patient in the system.
     *
     * @param patient The patient details to save.
     * @return The saved patient entity with its generated ID.
     */
    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        logger.info("Creating new patient: FirstName={}, LastName={}", patient.getFirstName(), patient.getLastName());
        Patient savedPatient = patientService.createPatient(patient);
        logger.info("Patient created with id={}", savedPatient.getId());
        return savedPatient;
    }

    /**
     * Fetches a single patient by their unique identifier.
     *
     * @param id The ID of the patient to find.
     * @return A ResponseEntity containing the patient if found, or 404 if not.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        logger.info("Fetching patient with id={}", id);
        return patientService.getPatientById(id)
                .map(patient -> {
                    logger.info("Patient found with id={}", id);
                    return ResponseEntity.ok(patient);
                })
                .orElseGet(() -> {
                    logger.warn("Patient not found with id={}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patientDetails) {
        logger.info("Updating patient with id={}", id);
        return patientService.updatePatient(id, patientDetails)
                .map(patient -> {
                    logger.info("Patient updated with id={}", id);
                    return ResponseEntity.ok(patient);
                })
                .orElseGet(() -> {
                    logger.warn("Patient not found for update with id={}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        logger.info("Deleting patient with id={}", id);
        if (patientService.deletePatient(id)) {
            logger.info("Patient deleted with id={}", id);
            return ResponseEntity.ok().build();
        } else {
            logger.warn("Patient not found for deletion with id={}", id);
            return ResponseEntity.notFound().build();
        }
    }
}