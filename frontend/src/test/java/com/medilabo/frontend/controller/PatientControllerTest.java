package com.medilabo.frontend.controller;

import com.medilabo.frontend.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @Test
    void testGetPatients() throws Exception {
        // Mock list of patients returned from service
        List<Map> mockPatients = List.of(
                Map.of("id", 1, "firstName", "Alice", "lastName", "Smith"),
                Map.of("id", 2, "firstName", "Bob", "lastName", "Brown")
        );
        when(patientService.getAllPatients()).thenReturn(mockPatients);

        // Perform GET /
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients"))
                .andExpect(model().attributeExists("patients"))
                .andExpect(model().attribute("patients", mockPatients));

        verify(patientService, times(1)).getAllPatients();
    }

    @Test
    void testAddPatient() throws Exception {
        mockMvc.perform(post("/patients/add")
                        .param("firstName", "John")
                        .param("lastName", "Doe"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(patientService, times(1)).addPatient("John", "Doe");
    }

    @Test
    void testUpdatePatient() throws Exception {
        mockMvc.perform(post("/patients/update")
                        .param("id", "1")
                        .param("firstName", "Jane")
                        .param("lastName", "Doe"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(patientService, times(1)).updatePatient(1L, "Jane", "Doe");
    }

    @Test
    void testDeletePatient() throws Exception {
        mockMvc.perform(post("/patients/delete")
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(patientService, times(1)).deletePatient(1L);
    }
}
