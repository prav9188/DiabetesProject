package com.medilabo.physiciannotes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PhysicianNotesApplication {
    private static final Logger logger = LoggerFactory.getLogger(PhysicianNotesApplication.class);

    public static void main(String[] args) {
        logger.info("Starting Physician Notes microservice...");
        SpringApplication.run(PhysicianNotesApplication.class, args);
        logger.info("Physician Notes microservice started successfully.");
    }
}
