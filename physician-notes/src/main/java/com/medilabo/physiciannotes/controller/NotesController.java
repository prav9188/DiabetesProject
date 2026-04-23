package com.medilabo.physiciannotes.controller;

import com.medilabo.physiciannotes.entity.Note;
import com.medilabo.physiciannotes.service.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing physician notes.
 * Uses MongoDB (NoSQL) for flexible document storage of unstructured medical notes.
 */
@RestController
@RequestMapping("/notes")
public class NotesController {

    private static final Logger logger = LoggerFactory.getLogger(NotesController.class);
    private final NoteService noteService;

    public NotesController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<Note> getAllNotes() {
        logger.info("Request to get all notes");
        return noteService.getAllNotes();
    }

    /**
     * Retrieves all medical notes for a specific patient.
     *
     * @param patientId The unique ID of the patient.
     * @return A list of notes associated with the patient.
     */
    @GetMapping("/patient/{patientId}")
    public List<Note> getNotesByPatientId(@PathVariable Long patientId) {
        logger.info("Fetching notes for patient id={}", patientId);
        return noteService.getNotesByPatientId(patientId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable String id) {
        logger.info("Request to get note with id={}", id);
        return noteService.getNoteById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("Note not found with id={}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    /**
     * Adds a new physician note for a patient.
     *
     * @param note The note content and metadata.
     * @return The saved note entity.
     */
    @PostMapping
    public Note createNote(@RequestBody Note note) {
        logger.info("Request to create note for patient id={}", note.getPatientId());
        return noteService.createNote(note);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable String id, @RequestBody Note noteDetails) {
        logger.info("Request to update note with id={}", id);
        return noteService.updateNote(id, noteDetails)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("Note not found for update with id={}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable String id) {
        logger.info("Request to delete note with id={}", id);
        if (noteService.deleteNote(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/test")
    public String test() {
        return "Physician Notes microservice is running!";
    }
}
