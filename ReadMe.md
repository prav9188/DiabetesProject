# Project Diabetes Management - Sprint Documentation

## Sprint 1 - Patient Management Microservice, Gateway, and Frontend V1

### Sprint 1 Summary
This sprint focuses on developing the initial architecture of the application with the patient management microservice, a gateway for request routing, and the first version of the frontend UI. The goal is to ensure seamless communication between these components, with basic security, data persistence using PostgreSQL, and comprehensive functional testing. Global logging and test coverage have also been implemented.

---

### Entry Criteria
- Understanding of user stories and business context.
- Development environment setup (Java, Spring Boot, PostgreSQL).
- Initial configuration of the patient-management, gateway, and frontend microservices.
- Documentation and backlog review to define sprint tasks.

---

### Exit Criteria
- Successful build of all modules (patient-management, gateway, frontend).
- Fully operational Patient Management microservice with PostgreSQL connection configured.
- REST endpoints (GET, POST, PUT, DELETE) tested and working via tools such as Bruno.
- Gateway configured to route requests appropriately to microservices.
- Spring Boot + Thymeleaf frontend working, displaying patient data and supporting CRUD operations.
- Basic authentication securing the endpoints.
- Global logging implemented for all microservices.
- Unit and integration tests covered with Jacoco and Surefire.
- Verified concurrent execution of microservices in separate terminals.
- Clear and comprehensive documentation on setup and functionality.

---

### Sprint 1 Checklist - Mapping to Completed Tasks

| Task                                                                 | Status     |
|----------------------------------------------------------------------|------------|
| Successful build on all modules                                       | ✔ Completed|
| PostgreSQL connection configuration in patient-management             | ✔ Completed|
| Created PatientManagementApplication                                  | ✔ Completed|
| Security Configuration implemented                                   | ✔ Completed|
| REST endpoints tested for patient-management microservice            | ✔ Completed|
| Verified patient data persisted in PostgreSQL                        | ✔ Completed|
| Configured Gateway (application.properties, security, gateway app)   | ✔ Completed|
| Tested GET, POST, PUT, DELETE via Gateway on separate terminals      | ✔ Completed|
| Implemented frontend Spring Boot + Thymeleaf with CRUD               | ✔ Completed|
| Frontend REST operations (GET, POST, DELETE, PUT) tested             | ✔ Completed|
| Global logging setup for all microservices                           | ✔ Completed|
| Jacoco and Surefire integrated for test coverage                     | ✔ Completed|
| Added tests for frontend microservice                                | ✔ Completed|

---

## Sprint 2 - Physician Notes Microservice, MongoDB, and Frontend V2

### Sprint 2 Summary
This sprint focuses on the integration of the Physician Notes microservice, which allows doctors to add, edit, and delete notes for each patient. Data is persisted in a NoSQL MongoDB database. The gateway has been updated to route requests to this new microservice, and the frontend has been enhanced to provide a dedicated interface for managing patient notes.

---

### Entry Criteria
- Functional architecture from Sprint 1 (Patient Management, Gateway, Frontend).
- MongoDB installed and configured locally.
- Understanding of NoSQL data modeling for patient notes.
- Updated backlog with tasks for notes management.

---

### Exit Criteria
- Successful build of the new `physician-notes` module.
- Fully operational Physician Notes microservice with MongoDB connection.
- REST endpoints for notes (GET, POST, PUT, DELETE) tested and working.
- Gateway updated to route `/notes/**` requests to the notes microservice.
- Frontend updated with a new "Notes" view and CRUD operations for notes.
- Unit and integration tests for the notes microservice with high coverage.
- End-to-end flow verified: Patient -> Notes interaction.

---

### Sprint 2 Checklist - Mapping to Completed Tasks

| Task                                                                 | Status     |
|----------------------------------------------------------------------|------------|
| Successful build of physician-notes module                           | ✔ Completed|
| MongoDB connection configuration in physician-notes                  | ✔ Completed|
| Implemented Notes REST controller and service                        | ✔ Completed|
| Integrated MongoDB for note persistence                              | ✔ Completed|
| Updated Gateway to route requests to physician-notes                 | ✔ Completed|
| Enhanced Frontend with dedicated Notes UI                            | ✔ Completed|
| Implemented Note CRUD operations in Frontend                         | ✔ Completed|
| Added unit and integration tests for physician-notes                 | ✔ Completed|
| Verified full integration between all microservices                  | ✔ Completed|

---

## Functional Flow Description

1. **Patient Management Microservice**:  
   Manages patient data persistence using a PostgreSQL database. Exposes secured REST endpoints (GET, POST, PUT, DELETE).

2. **Physician Notes Microservice**:  
   Manages clinical notes for patients using MongoDB. Exposes REST endpoints to create, read, update, and delete notes associated with a patient ID.

3. **Spring Cloud Gateway**:  
   Acts as the single entry point. It routes requests to `patient-management` (for `/patients/**`) and `physician-notes` (for `/notes/**`).

4. **Frontend Microservice**:  
   Provides a Thymeleaf-based UI. Users can view the patient list and click on a patient to manage their clinical notes. It communicates with both microservices via the gateway.

5. **Logging and Testing**:  
   Global logging tracks actions across all microservices. Jacoco and Surefire ensure high test coverage for all modules.

---

## How to Run the Microservices

- Start PostgreSQL and MongoDB services.
- Run `PatientManagementApplication` (patient-management module).
- Run `PhysicianNotesApplication` (physician-notes module).
- Run the Gateway application.
- Run the Frontend application.
- Access the frontend UI at `http://localhost:8080`.
- Use the UI to manage patients and their clinical notes.

---

## Sprint 3 - Diabetes Risk Microservice, Dockerization, and Green Code

### Sprint 3 Summary
This sprint introduces the Diabetes Risk microservice, which assesses a patient's risk level (None, Borderline, In Danger, Early Onset) based on their age, gender, and clinical triggers found in physician notes. The entire application has been containerized using Docker and orchestrated with Docker Compose. Additionally, "Green Code" best practices were researched and integrated into the development process.

---

### Entry Criteria
- Functional architecture from Sprint 2.
- Rules and trigger terms defined for risk assessment.
- Docker environment set up.

---

### Exit Criteria
- Successful build and integration of the `diabetes-risk` microservice.
- Patient Management microservice updated with additional fields (gender, phone, address).
- Frontend UI updated to manage full patient information and display risk level.
- Gateway updated to route requests to the new risk microservice.
- Dockerfiles created for all microservices.
- `docker-compose.yml` operational for the full stack (PostgreSQL, MongoDB, 5 microservices).
- Green Code practices documented.

---

### Sprint 3 Checklist - Mapping to Completed Tasks

| Task                                                                 | Status     |
|----------------------------------------------------------------------|------------|
| Updated Patient Management with Gender/Phone/Address                  | ✔ Completed|
| Implemented Diabetes Risk microservice with calculation logic        | ✔ Completed|
| Integrated WebClient in Risk service to fetch Patient/Notes data     | ✔ Completed|
| Updated Gateway to route `/risk/**` requests                         | ✔ Completed|
| Enhanced Frontend with Risk Assessment display                       | ✔ Completed|
| Dockerized all microservices (5 Dockerfiles)                         | ✔ Completed|
| Created and tested Docker Compose configuration                      | ✔ Completed|
| Research and Documentation of Green Code practices                   | ✔ Completed|

---
