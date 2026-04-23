package com.medilabo.controller;

import com.medilabo.entity.Patient;
import com.medilabo.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @Test
    @WithMockUser
    void testGetAllPatients() throws Exception {
        Patient p = new Patient();
        p.setId(1L);
        when(patientService.getAllPatients()).thenReturn(Arrays.asList(p));

        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    @WithMockUser
    void testGetPatientById() throws Exception {
        Patient p = new Patient();
        p.setId(1L);
        when(patientService.getPatientById(1L)).thenReturn(Optional.of(p));

        mockMvc.perform(get("/patients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser
    void testGetPatientById_NotFound() throws Exception {
        when(patientService.getPatientById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/patients/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void testCreatePatient() throws Exception {
        Patient p = new Patient();
        p.setId(1L);
        p.setFirstName("John");
        when(patientService.createPatient(any())).thenReturn(p);

        mockMvc.perform(post("/patients").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"John\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser
    void testUpdatePatient() throws Exception {
        Patient p = new Patient();
        p.setId(1L);
        when(patientService.updatePatient(eq(1L), any())).thenReturn(Optional.of(p));

        mockMvc.perform(put("/patients/1").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"Updated\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testDeletePatient() throws Exception {
        when(patientService.deletePatient(1L)).thenReturn(true);

        mockMvc.perform(delete("/patients/1").with(csrf()))
                .andExpect(status().isOk());
    }
}
