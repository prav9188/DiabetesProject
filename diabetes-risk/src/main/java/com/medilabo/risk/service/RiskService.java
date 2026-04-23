package com.medilabo.risk.service;

import com.medilabo.risk.model.NoteDTO;
import com.medilabo.risk.model.PatientDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service responsible for calculating the diabetes risk level for a patient.
 * It scans the patient's medical notes for specific trigger terms and evaluates
 * the risk based on age, gender, and the number of unique triggers found.
 */
@Service
public class RiskService {

    private static final Logger logger = LoggerFactory.getLogger(RiskService.class);

    private static final List<String> TRIGGER_TERMS = Arrays.asList(
            "Hemoglobin A1C", "Microalbumin", "Height", "Weight", "Smoking",
            "Abnormal", "Cholesterol", "Dizziness", "Relapse", "Reaction", "Antibody"
    );

    /**
     * Calculates the diabetes risk level for a patient based on age, gender, and medical notes.
     * The logic follows specific medical guidelines for different age groups and genders.
     *
     * @param patient The patient data including age and gender.
     * @param notes   The list of physician notes to scan for triggers.
     * @return A string representing the risk category (None, Borderline, In Danger, Early Onset).
     */
    public String calculateRisk(PatientDTO patient, List<NoteDTO> notes) {
        int age = patient.getAge();
        String gender = patient.getGender();
        long triggerCount = countUniqueTriggers(notes);

        logger.info("Calculating risk for patient id={}, age={}, gender={}, triggers={}", 
                patient.getId(), age, gender, triggerCount);

        if (age > 30) {
            if (triggerCount >= 8) return "Early Onset";
            if (triggerCount >= 6) return "In Danger";
            if (triggerCount >= 2) return "Borderline";
            return "None";
        } else {
            // Age <= 30
            if ("M".equalsIgnoreCase(gender)) {
                if (triggerCount >= 5) return "Early Onset";
                if (triggerCount >= 3) return "In Danger";
            } else if ("F".equalsIgnoreCase(gender)) {
                if (triggerCount >= 6) return "Early Onset";
                if (triggerCount >= 4) return "In Danger";
            }
            return "None";
        }
    }

    /**
     * Counts the number of unique trigger terms found in the patient's notes.
     * It uses a case-insensitive regex with word boundaries to ensure accurate matching.
     *
     * @param notes The list of notes to analyze.
     * @return The count of unique trigger terms found.
     */
    private long countUniqueTriggers(List<NoteDTO> notes) {
        if (notes == null || notes.isEmpty()) return 0;

        String allContent = notes.stream()
                .map(NoteDTO::getContent)
                .filter(content -> content != null)
                .collect(Collectors.joining(" "))
                .toLowerCase();

        return TRIGGER_TERMS.stream()
                .filter(term -> {
                    String termLower = term.toLowerCase();
                    // Regex to find term as a whole word, case-insensitive
                    String regex = "(?i)\\b" + java.util.regex.Pattern.quote(termLower) + "\\b";
                    return java.util.regex.Pattern.compile(regex).matcher(allContent).find();
                })
                .count();
    }
}