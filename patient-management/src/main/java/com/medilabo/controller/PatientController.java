package com.medilabo.controller;

import com.medilabo.entity.Patient;
import com.medilabo.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    private final PatientRepository repository;

    public PatientController(PatientRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Patient> getAllPatients() {
        logger.info("Request to get all patients");
        return repository.findAll();
    }

    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        logger.info("Creating new patient: FirstName={}, LastName={}", patient.getFirstName(), patient.getLastName());
        Patient savedPatient = repository.save(patient);
        logger.info("Patient created with id={}", savedPatient.getId());
        return savedPatient;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        logger.info("Fetching patient with id={}", id);
        return repository.findById(id)
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
        return repository.findById(id)
                .map(patient -> {
                    patient.setFirstName(patientDetails.getFirstName());
                    patient.setLastName(patientDetails.getLastName());
                    patient.setEmail(patientDetails.getEmail());
                    patient.setDateOfBirth(patientDetails.getDateOfBirth());
                    repository.save(patient);
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
        return repository.findById(id)
                .map(patient -> {
                    repository.delete(patient);
                    logger.info("Patient deleted with id={}", id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> {
                    logger.warn("Patient not found for deletion with id={}", id);
                    return ResponseEntity.notFound().build();
                });
    }
}