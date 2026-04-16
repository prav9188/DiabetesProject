package com.medilabo.frontend.controller;

import com.medilabo.frontend.service.NoteService;
import com.medilabo.frontend.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class PatientController {

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    private final PatientService patientService;
    private final NoteService noteService;

    public PatientController(PatientService patientService, NoteService noteService) {
        this.patientService = patientService;
        this.noteService = noteService;
    }

    @GetMapping("/")
    public String getPatients(Model model) {
        logger.info("Fetching patients list");
        List<Map> patients = patientService.getAllPatients();

        model.addAttribute("patients", patients);
        logger.info("Patients list loaded with {} entries", patients != null ? patients.size() : 0);
        return "patients";
    }

    @GetMapping("/notes/{patientId}")
    public String getNotes(@PathVariable Long patientId, Model model) {
        logger.info("Fetching notes for patient id={}", patientId);
        List<Map> notes = noteService.getNotesByPatientId(patientId);
        model.addAttribute("notes", notes);
        model.addAttribute("patientId", patientId);
        return "notes";
    }

    @PostMapping("/notes/add")
    public String addNote(@RequestParam Long patientId, @RequestParam String content) {
        logger.info("Adding note for patient id={}", patientId);
        noteService.addNote(patientId, content);
        return "redirect:/notes/" + patientId;
    }

    @PostMapping("/notes/update")
    public String updateNote(@RequestParam String id, @RequestParam Long patientId, @RequestParam String content) {
        logger.info("Updating note id={}", id);
        noteService.updateNote(id, content);
        return "redirect:/notes/" + patientId;
    }

    @PostMapping("/notes/delete")
    public String deleteNote(@RequestParam String id, @RequestParam Long patientId) {
        logger.info("Deleting note id={}", id);
        noteService.deleteNote(id);
        return "redirect:/notes/" + patientId;
    }

    @PostMapping("/patients/add")
    public String addPatient(@RequestParam String firstName,
                             @RequestParam String lastName) {
        logger.info("Adding new patient: FirstName={}, LastName={}", firstName, lastName);
        patientService.addPatient(firstName, lastName);
        logger.info("Patient added successfully");
        return "redirect:/";
    }

    @PostMapping("/patients/update")
    public String updatePatient(@RequestParam Long id,
                                @RequestParam String firstName,
                                @RequestParam String lastName) {
        logger.info("Updating patient id={}: FirstName={}, LastName={}", id, firstName, lastName);
        patientService.updatePatient(id, firstName, lastName);
        logger.info("Patient id={} updated successfully", id);
        return "redirect:/";
    }

    @PostMapping("/patients/delete")
    public String deletePatient(@RequestParam Long id) {
        logger.info("Deleting patient with id={}", id);
        patientService.deletePatient(id);
        logger.info("Patient id={} deleted successfully", id);
        return "redirect:/";
    }
}