package com.medilabo.service;

import com.medilabo.entity.Patient;
import java.util.List;
import java.util.Optional;

public interface PatientService {
    List<Patient> getAllPatients();
    Patient createPatient(Patient patient);
    Optional<Patient> getPatientById(Long id);
    Optional<Patient> updatePatient(Long id, Patient patientDetails);
    boolean deletePatient(Long id);
}
