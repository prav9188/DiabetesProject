package com.medilabo.physiciannotes.controller;

import com.medilabo.physiciannotes.entity.Note;
import com.medilabo.physiciannotes.service.NoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class NotesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    @Test
    @WithMockUser(username = "medilabo_user", roles = "USER")
    void testGetAllNotes() throws Exception {
        when(noteService.getAllNotes()).thenReturn(Arrays.asList(new Note(1L, "Content")));

        mockMvc.perform(get("/notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value("Content"));
    }

    @Test
    @WithMockUser(username = "medilabo_user", roles = "USER")
    void testGetNotesByPatientId() throws Exception {
        when(noteService.getNotesByPatientId(1L)).thenReturn(Arrays.asList(new Note(1L, "Content")));

        mockMvc.perform(get("/notes/patient/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value("Content"));
    }

    @Test
    @WithMockUser(username = "medilabo_user", roles = "USER")
    void testGetNoteById() throws Exception {
        Note note = new Note(1L, "Content");
        note.setId("id1");
        when(noteService.getNoteById("id1")).thenReturn(Optional.of(note));

        mockMvc.perform(get("/notes/id1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("id1"));
    }

    @Test
    @WithMockUser(username = "medilabo_user", roles = "USER")
    void testCreateNote() throws Exception {
        Note note = new Note(1L, "New Note");
        when(noteService.createNote(any(Note.class))).thenReturn(note);

        mockMvc.perform(post("/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"patientId\": 1, \"content\": \"New Note\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("New Note"));
    }

    @Test
    @WithMockUser(username = "medilabo_user", roles = "USER")
    void testUpdateNote() throws Exception {
        Note note = new Note(1L, "Updated Note");
        when(noteService.updateNote(anyString(), any(Note.class))).thenReturn(Optional.of(note));

        mockMvc.perform(put("/notes/id1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"content\": \"Updated Note\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Updated Note"));
    }

    @Test
    @WithMockUser(username = "medilabo_user", roles = "USER")
    void testDeleteNote() throws Exception {
        when(noteService.deleteNote("id1")).thenReturn(true);

        mockMvc.perform(delete("/notes/id1"))
                .andExpect(status().isOk());
    }

    @Test
    void testUnauthenticatedAccess() throws Exception {
        mockMvc.perform(get("/notes"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testAuthenticatedWithHttpBasic() throws Exception {
        when(noteService.getAllNotes()).thenReturn(Arrays.asList());
        mockMvc.perform(get("/notes").with(httpBasic("medilabo_user", "9188")))
                .andExpect(status().isOk());
    }
}
