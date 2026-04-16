package com.medilabo.physiciannotes.service;

import com.medilabo.physiciannotes.entity.Note;
import com.medilabo.physiciannotes.repository.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {

    private static final Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);
    private final NoteRepository noteRepository;

    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public List<Note> getAllNotes() {
        logger.info("Fetching all notes");
        return noteRepository.findAll();
    }

    @Override
    public List<Note> getNotesByPatientId(Long patientId) {
        logger.info("Fetching notes for patient id={}", patientId);
        return noteRepository.findByPatientId(patientId);
    }

    @Override
    public Optional<Note> getNoteById(String id) {
        logger.info("Fetching note with id={}", id);
        return noteRepository.findById(id).map(note -> {
            logger.info("Note found with id={}", id);
            return note;
        });
    }

    @Override
    public Note createNote(Note note) {
        logger.info("Creating new note for patient id={}", note.getPatientId());
        Note savedNote = noteRepository.save(note);
        logger.info("Note created with id={}", savedNote.getId());
        return savedNote;
    }

    @Override
    public Optional<Note> updateNote(String id, Note noteDetails) {
        logger.info("Updating note with id={}", id);
        return noteRepository.findById(id).map(note -> {
            note.setContent(noteDetails.getContent());
            Note updatedNote = noteRepository.save(note);
            logger.info("Note updated with id={}", id);
            return updatedNote;
        });
    }

    @Override
    public boolean deleteNote(String id) {
        logger.info("Deleting note with id={}", id);
        if (noteRepository.existsById(id)) {
            noteRepository.deleteById(id);
            logger.info("Note deleted with id={}", id);
            return true;
        }
        logger.warn("Note not found for deletion with id={}", id);
        return false;
    }
}
