package com.medilabo.physiciannotes.repository;

import com.medilabo.physiciannotes.entity.Note;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
@ActiveProfiles("test")
@Disabled("Disabled due to lack of embedded MongoDB in environment")
public class NoteRepositoryTest {

    @Autowired
    private NoteRepository noteRepository;

    @BeforeEach
    void setUp() {
        noteRepository.save(new Note(1L, "Note 1 for patient 1"));
        noteRepository.save(new Note(1L, "Note 2 for patient 1"));
        noteRepository.save(new Note(2L, "Note 1 for patient 2"));
    }

    @AfterEach
    void tearDown() {
        noteRepository.deleteAll();
    }

    @Test
    void testFindByPatientId() {
        List<Note> notes = noteRepository.findByPatientId(1L);
        assertEquals(2, notes.size());
        assertTrue(notes.stream().allMatch(n -> n.getPatientId().equals(1L)));
    }

    @Test
    void testFindByPatientId_NoNotes() {
        List<Note> notes = noteRepository.findByPatientId(99L);
        assertEquals(0, notes.size());
    }
}
