package com.medilabo.physiciannotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class PhysicianNotesApplication {
    public static void main(String[] args) {
        SpringApplication.run(PhysicianNotesApplication.class, args);
    }
}
