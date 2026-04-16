package com.medilabo.physiciannotes.service;

import com.medilabo.physiciannotes.entity.Note;
import com.medilabo.physiciannotes.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteServiceImpl noteService;

    private Note note1;
    private Note note2;

    @BeforeEach
    void setUp() {
        note1 = new Note(1L, "Content 1");
        note1.setId("id1");
        note2 = new Note(1L, "Content 2");
        note2.setId("id2");
    }

    @Test
    void testGetAllNotes() {
        when(noteRepository.findAll()).thenReturn(Arrays.asList(note1, note2));
        List<Note> result = noteService.getAllNotes();
        assertEquals(2, result.size());
        verify(noteRepository, times(1)).findAll();
    }

    @Test
    void testGetNotesByPatientId() {
        when(noteRepository.findByPatientId(1L)).thenReturn(Arrays.asList(note1, note2));
        List<Note> result = noteService.getNotesByPatientId(1L);
        assertEquals(2, result.size());
        verify(noteRepository, times(1)).findByPatientId(1L);
    }

    @Test
    void testGetNoteById() {
        when(noteRepository.findById("id1")).thenReturn(Optional.of(note1));
        Optional<Note> result = noteService.getNoteById("id1");
        assertTrue(result.isPresent());
        assertEquals("Content 1", result.get().getContent());
    }

    @Test
    void testCreateNote() {
        when(noteRepository.save(any(Note.class))).thenReturn(note1);
        Note result = noteService.createNote(new Note(1L, "Content 1"));
        assertNotNull(result);
        assertEquals("id1", result.getId());
    }

    @Test
    void testUpdateNote() {
        when(noteRepository.findById("id1")).thenReturn(Optional.of(note1));
        when(noteRepository.save(any(Note.class))).thenReturn(note1);

        Note updateDetails = new Note(1L, "Updated Content");
        Optional<Note> result = noteService.updateNote("id1", updateDetails);

        assertTrue(result.isPresent());
        assertEquals("Updated Content", result.get().getContent());
    }

    @Test
    void testUpdateNote_NotFound() {
        when(noteRepository.findById("nonexistent")).thenReturn(Optional.empty());
        Optional<Note> result = noteService.updateNote("nonexistent", new Note(1L, "Content"));
        assertFalse(result.isPresent());
    }

    @Test
    void testDeleteNote() {
        when(noteRepository.existsById("id1")).thenReturn(true);
        doNothing().when(noteRepository).deleteById("id1");

        boolean result = noteService.deleteNote("id1");
        assertTrue(result);
        verify(noteRepository, times(1)).deleteById("id1");
    }

    @Test
    void testDeleteNote_NotFound() {
        when(noteRepository.existsById("nonexistent")).thenReturn(false);
        boolean result = noteService.deleteNote("nonexistent");
        assertFalse(result);
        verify(noteRepository, never()).deleteById(anyString());
    }
}
