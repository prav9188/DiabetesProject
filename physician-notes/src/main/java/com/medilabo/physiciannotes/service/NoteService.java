package com.medilabo.physiciannotes.service;

import com.medilabo.physiciannotes.entity.Note;
import java.util.List;
import java.util.Optional;

public interface NoteService {
    List<Note> getAllNotes();
    List<Note> getNotesByPatientId(Long patientId);
    Optional<Note> getNoteById(String id);
    Note createNote(Note note);
    Optional<Note> updateNote(String id, Note noteDetails);
    boolean deleteNote(String id);
}
