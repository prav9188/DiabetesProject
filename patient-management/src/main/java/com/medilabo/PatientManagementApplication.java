package com.medilabo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PatientManagementApplication {
    private static final Logger logger = LoggerFactory.getLogger(PatientManagementApplication.class);

    public static void main(String[] args) {
        logger.info("Starting Patient Management microservice...");
        SpringApplication.run(PatientManagementApplication.class, args);
        logger.info("Patient Management microservice started successfully.");
    }
}