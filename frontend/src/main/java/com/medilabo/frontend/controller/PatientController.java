package com.medilabo.frontend.controller;

import com.medilabo.frontend.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class PatientController {

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/")
    public String getPatients(Model model) {
        logger.info("Fetching patients list");
        List<Map> patients = patientService.getAllPatients();

        model.addAttribute("patients", patients);
        logger.info("Patients list loaded with {} entries", patients != null ? patients.size() : 0);
        return "patients";
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