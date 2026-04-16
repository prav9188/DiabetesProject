package com.medilabo.frontend.service;

import java.util.List;
import java.util.Map;

public interface NoteService {
    List<Map> getNotesByPatientId(Long patientId);
    void addNote(Long patientId, String content);
    void updateNote(String id, String content);
    void deleteNote(String id);
}
