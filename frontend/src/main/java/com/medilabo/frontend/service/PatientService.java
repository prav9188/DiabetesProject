package com.medilabo.frontend.service;

import java.util.List;
import java.util.Map;

public interface PatientService {
    List<Map> getAllPatients();
    void addPatient(String firstName, String lastName, String email, String dateOfBirth, String gender, String phone, String address);
    void updatePatient(Long id, String firstName, String lastName, String email, String dateOfBirth, String gender, String phone, String address);
    void deletePatient(Long id);
}
