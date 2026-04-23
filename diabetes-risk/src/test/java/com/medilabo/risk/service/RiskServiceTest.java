package com.medilabo.risk.service;

import com.medilabo.risk.model.NoteDTO;
import com.medilabo.risk.model.PatientDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RiskServiceTest {

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

    private List<NoteDTO> createNotes(String... contents) {
        List<NoteDTO> notes = new ArrayList<>();
        for (String content : contents) {
            NoteDTO note = new NoteDTO();
            note.setContent(content);
            notes.add(note);
        }
        return notes;
    }

    @Test
    public void testRiskNone_Over30() {
        PatientDTO patient = createPatient("M", 35);
        List<NoteDTO> notes = createNotes("Healthy patient");
        assertEquals("None", riskService.calculateRisk(patient, notes));
    }

    @Test
    public void testRiskBorderline_Over30() {
        PatientDTO patient = createPatient("M", 35);
        List<NoteDTO> notes = createNotes("Weight", "Height");
        assertEquals("Borderline", riskService.calculateRisk(patient, notes));
    }

    @Test
    public void testRiskInDanger_Over30() {
        PatientDTO patient = createPatient("M", 35);
        List<NoteDTO> notes = createNotes("Weight", "Height", "Cholesterol", "Abnormal", "Smoking", "Microalbumin");
        assertEquals("In Danger", riskService.calculateRisk(patient, notes));
    }

    @Test
    public void testRiskEarlyOnset_Over30() {
        PatientDTO patient = createPatient("M", 35);
        List<NoteDTO> notes = createNotes("Weight", "Height", "Cholesterol", "Abnormal", "Smoking", "Microalbumin", "Dizziness", "Relapse");
        assertEquals("Early Onset", riskService.calculateRisk(patient, notes));
    }

    @Test
    public void testRiskNone_Under30_Male() {
        PatientDTO patient = createPatient("M", 25);
        List<NoteDTO> notes = createNotes("Weight", "Height");
        assertEquals("None", riskService.calculateRisk(patient, notes));
    }

    @Test
    public void testRiskInDanger_Under30_Male() {
        PatientDTO patient = createPatient("M", 25);
        List<NoteDTO> notes = createNotes("Weight", "Height", "Cholesterol");
        assertEquals("In Danger", riskService.calculateRisk(patient, notes));
    }

    @Test
    public void testRiskEarlyOnset_Under30_Male() {
        PatientDTO patient = createPatient("M", 25);
        List<NoteDTO> notes = createNotes("Weight", "Height", "Cholesterol", "Abnormal", "Smoking");
        assertEquals("Early Onset", riskService.calculateRisk(patient, notes));
    }

    @Test
    public void testRiskNone_Under30_Female() {
        PatientDTO patient = createPatient("F", 25);
        List<NoteDTO> notes = createNotes("Weight", "Height", "Cholesterol");
        assertEquals("None", riskService.calculateRisk(patient, notes));
    }

    @Test
    public void testRiskInDanger_Under30_Female() {
        PatientDTO patient = createPatient("F", 25);
        List<NoteDTO> notes = createNotes("Weight", "Height", "Cholesterol", "Abnormal");
        assertEquals("In Danger", riskService.calculateRisk(patient, notes));
    }

    @Test
    public void testRiskEarlyOnset_Under30_Female() {
        PatientDTO patient = createPatient("F", 25);
        List<NoteDTO> notes = createNotes("Weight", "Height", "Cholesterol", "Abnormal", "Smoking", "Microalbumin");
        assertEquals("Early Onset", riskService.calculateRisk(patient, notes));
    }

    @Test
    public void testRiskNone_EmptyNotes() {
        PatientDTO patient = createPatient("M", 35);
        assertEquals("None", riskService.calculateRisk(patient, null));
    }
}
