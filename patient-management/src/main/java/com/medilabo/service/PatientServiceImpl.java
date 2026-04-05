package com.medilabo.service;

import com.medilabo.entity.Patient;
import com.medilabo.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    private static final Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);
    private final PatientRepository repository;

    public PatientServiceImpl(PatientRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Patient> getAllPatients() {
        logger.info("Service layer: fetching all patients");
        return repository.findAll();
    }

    @Override
    public Patient createPatient(Patient patient) {
        logger.info("Service layer: creating patient");
        return repository.save(patient);
    }

    @Override
    public Optional<Patient> getPatientById(Long id) {
        logger.info("Service layer: fetching patient id={}", id);
        return repository.findById(id);
    }

    @Override
    public Optional<Patient> updatePatient(Long id, Patient patientDetails) {
        logger.info("Service layer: updating patient id={}", id);
        return repository.findById(id)
                .map(patient -> {
                    patient.setFirstName(patientDetails.getFirstName());
                    patient.setLastName(patientDetails.getLastName());
                    patient.setEmail(patientDetails.getEmail());
                    patient.setDateOfBirth(patientDetails.getDateOfBirth());
                    return repository.save(patient);
                });
    }

    @Override
    public boolean deletePatient(Long id) {
        logger.info("Service layer: deleting patient id={}", id);
        return repository.findById(id)
                .map(patient -> {
                    repository.delete(patient);
                    return true;
                })
                .orElse(false);
    }
}
