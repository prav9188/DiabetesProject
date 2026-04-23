package com.medilabo.risk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DiabetesRiskApplication {
    private static final Logger logger = LoggerFactory.getLogger(DiabetesRiskApplication.class);
    public static void main(String[] args) {
        logger.info("Starting Diabetes Risk Assessment microservice...");
        SpringApplication.run(DiabetesRiskApplication.class, args);
        logger.info("Diabetes Risk Assessment microservice started successfully.");
    }
}