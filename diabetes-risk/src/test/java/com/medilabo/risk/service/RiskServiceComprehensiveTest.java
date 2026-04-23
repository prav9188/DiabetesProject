package com.medilabo.risk.service;

import com.medilabo.risk.model.NoteDTO;
import com.medilabo.risk.model.PatientDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RiskServiceComprehensiveTest {

    private RiskService riskService;

    @BeforeEach
    public void setUp() {
        riskService = new RiskService();
    }

    private PatientDTO createPatient(String gender, int age) {
        PatientDTO patient = new PatientDTO();
        patient.setId(1L);
        patient.setGender(gender);
        patient.setDateOfBirth(LocalDate.now().minusYears(age).toString());
        return patient;
    }

    private List<NoteDTO> createNotes(int count) {
        String[] triggers = {
            "Hemoglobin A1C", "Microalbumin", "Height", "Weight", "Smoking",
            "Abnormal", "Cholesterol", "Dizziness", "Relapse", "Reaction", "Antibody"
        };
        List<NoteDTO> notes = new ArrayList<>();
        for (int i = 0; i < count && i < triggers.length; i++) {
            NoteDTO note = new NoteDTO();
            note.setContent(triggers[i]);
            notes.add(note);
        }
        return notes;
    }

    // Rules for Over 30
    // None: < 2 triggers
    // Borderline: 2-5 triggers
    // In Danger: 6-7 triggers
    // Early Onset: 8+ triggers

    @Test
    public void testOver30_None() {
        PatientDTO patient = createPatient("M", 35);
        assertEquals("None", riskService.calculateRisk(patient, createNotes(0)));
        assertEquals("None", riskService.calculateRisk(patient, createNotes(1)));
    }

    @Test
    public void testOver30_Borderline() {
        PatientDTO patient = createPatient("M", 35);
        assertEquals("Borderline", riskService.calculateRisk(patient, createNotes(2)));
        assertEquals("Borderline", riskService.calculateRisk(patient, createNotes(5)));
    }

    @Test
    public void testOver30_InDanger() {
        PatientDTO patient = createPatient("M", 35);
        assertEquals("In Danger", riskService.calculateRisk(patient, createNotes(6)));
        assertEquals("In Danger", riskService.calculateRisk(patient, createNotes(7)));
    }

    @Test
    public void testOver30_EarlyOnset() {
        PatientDTO patient = createPatient("M", 35);
        assertEquals("Early Onset", riskService.calculateRisk(patient, createNotes(8)));
        assertEquals("Early Onset", riskService.calculateRisk(patient, createNotes(11)));
    }

    // Rules for Under 30 Male
    // None: < 3 triggers
    // In Danger: 3-4 triggers
    // Early Onset: 5+ triggers

    @Test
    public void testUnder30_Male_None() {
        PatientDTO patient = createPatient("M", 25);
        assertEquals("None", riskService.calculateRisk(patient, createNotes(0)));
        assertEquals("None", riskService.calculateRisk(patient, createNotes(2)));
    }

    @Test
    public void testUnder30_Male_InDanger() {
        PatientDTO patient = createPatient("M", 25);
        assertEquals("In Danger", riskService.calculateRisk(patient, createNotes(3)));
        assertEquals("In Danger", riskService.calculateRisk(patient, createNotes(4)));
    }

    @Test
    public void testUnder30_Male_EarlyOnset() {
        PatientDTO patient = createPatient("M", 25);
        assertEquals("Early Onset", riskService.calculateRisk(patient, createNotes(5)));
    }

    // Rules for Under 30 Female
    // None: < 4 triggers
    // In Danger: 4-5 triggers
    // Early Onset: 6+ triggers

    @Test
    public void testUnder30_Female_None() {
        PatientDTO patient = createPatient("F", 25);
        assertEquals("None", riskService.calculateRisk(patient, createNotes(0)));
        assertEquals("None", riskService.calculateRisk(patient, createNotes(3)));
    }

    @Test
    public void testUnder30_Female_InDanger() {
        PatientDTO patient = createPatient("F", 25);
        assertEquals("In Danger", riskService.calculateRisk(patient, createNotes(4)));
        assertEquals("In Danger", riskService.calculateRisk(patient, createNotes(5)));
    }

    @Test
    public void testUnder30_Female_EarlyOnset() {
        PatientDTO patient = createPatient("F", 25);
        assertEquals("Early Onset", riskService.calculateRisk(patient, createNotes(6)));
    }

    @Test
    public void testTriggerWordBoundaries() {
        PatientDTO patient = createPatient("M", 35);
        NoteDTO note = new NoteDTO();
        note.setContent("The patient has no reactionary issues or Relapsed symptoms."); // contains "Relapse" (whole word) and "reactionary" (not whole word)
        List<NoteDTO> notes = List.of(note);
        
        // Should only count "Relapse" because "reactionary" is not "Reaction"
        // triggers: 1 -> None (Over 30 needs 2 for Borderline)
        assertEquals("None", riskService.calculateRisk(patient, notes));
        
        note.setContent("Reaction occurred. Relapse noted.");
        notes = List.of(note);
        // triggers: 2 ("Reaction", "Relapse") -> Borderline
        assertEquals("Borderline", riskService.calculateRisk(patient, notes));
    }
}
