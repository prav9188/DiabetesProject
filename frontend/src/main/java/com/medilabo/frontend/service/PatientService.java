package com.medilabo.frontend.service;

import java.util.List;
import java.util.Map;

public interface PatientService {
    List<Map> getAllPatients();
    void addPatient(String firstName, String lastName);
    void updatePatient(Long id, String firstName, String lastName);
    void deletePatient(Long id);
}
