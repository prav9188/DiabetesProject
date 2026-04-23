package com.medilabo.service;

import com.medilabo.entity.Patient;
import com.medilabo.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PatientServiceTest {

    private PatientRepository repository;
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(PatientRepository.class);
        patientService = new PatientServiceImpl(repository);
    }

    @Test
    void testGetAllPatients() {
        Patient p = new Patient();
        when(repository.findAll()).thenReturn(Arrays.asList(p));
        List<Patient> result = patientService.getAllPatients();
        assertEquals(1, result.size());
    }

    @Test
    void testCreatePatient() {
        Patient p = new Patient();
        when(repository.save(any())).thenReturn(p);
        Patient result = patientService.createPatient(p);
        assertNotNull(result);
    }

    @Test
    void testGetPatientById() {
        Patient p = new Patient();
        when(repository.findById(1L)).thenReturn(Optional.of(p));
        Optional<Patient> result = patientService.getPatientById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void testUpdatePatient() {
        Patient existing = new Patient();
        existing.setFirstName("Old");
        Patient update = new Patient();
        update.setFirstName("New");
        
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any())).thenReturn(existing);
        
        Optional<Patient> result = patientService.updatePatient(1L, update);
        assertTrue(result.isPresent());
        assertEquals("New", result.get().getFirstName());
    }

    @Test
    void testDeletePatient() {
        Patient p = new Patient();
        when(repository.findById(1L)).thenReturn(Optional.of(p));
        boolean result = patientService.deletePatient(1L);
        assertTrue(result);
        verify(repository, times(1)).delete(p);
    }
}
