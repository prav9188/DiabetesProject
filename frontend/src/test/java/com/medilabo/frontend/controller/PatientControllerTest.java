package com.medilabo.frontend.controller;

import com.medilabo.frontend.service.NoteService;
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

    @MockBean
    private NoteService noteService;

    @MockBean
    private com.medilabo.frontend.service.RiskService riskService;

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
                        .param("lastName", "Doe")
                        .param("email", "john@doe.com")
                        .param("dateOfBirth", "1990-01-01")
                        .param("gender", "M")
                        .param("phone", "123-456")
                        .param("address", "123 Main St"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(patientService, times(1)).addPatient("John", "Doe", "john@doe.com", "1990-01-01", "M", "123-456", "123 Main St");
    }

    @Test
    void testUpdatePatient() throws Exception {
        mockMvc.perform(post("/patients/update")
                        .param("id", "1")
                        .param("firstName", "Jane")
                        .param("lastName", "Doe")
                        .param("email", "jane@doe.com")
                        .param("dateOfBirth", "1990-01-01")
                        .param("gender", "F")
                        .param("phone", "123-456")
                        .param("address", "123 Main St"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(patientService, times(1)).updatePatient(1L, "Jane", "Doe", "jane@doe.com", "1990-01-01", "F", "123-456", "123 Main St");
    }

    @Test
    void testDeletePatient() throws Exception {
        mockMvc.perform(post("/patients/delete")
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(patientService, times(1)).deletePatient(1L);
    }

    @Test
    void testGetNotes() throws Exception {
        List<Map> mockNotes = List.of(
                Map.of("id", "note1", "patientId", 1L, "content", "Test note 1"),
                Map.of("id", "note2", "patientId", 1L, "content", "Test note 2")
        );
        when(noteService.getNotesByPatientId(1L)).thenReturn(mockNotes);

        mockMvc.perform(get("/notes/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("notes"))
                .andExpect(model().attributeExists("notes"))
                .andExpect(model().attribute("notes", mockNotes))
                .andExpect(model().attribute("patientId", 1L));

        verify(noteService, times(1)).getNotesByPatientId(1L);
    }

    @Test
    void testAddNote() throws Exception {
        mockMvc.perform(post("/notes/add")
                        .param("patientId", "1")
                        .param("content", "New note"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/notes/1"));

        verify(noteService, times(1)).addNote(1L, "New note");
    }

    @Test
    void testUpdateNote() throws Exception {
        mockMvc.perform(post("/notes/update")
                        .param("id", "note1")
                        .param("patientId", "1")
                        .param("content", "Updated note"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/notes/1"));

        verify(noteService, times(1)).updateNote("note1", "Updated note");
    }

    @Test
    void testDeleteNote() throws Exception {
        mockMvc.perform(post("/notes/delete")
                        .param("id", "note1")
                        .param("patientId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/notes/1"));

        verify(noteService, times(1)).deleteNote("note1");
    }
}
