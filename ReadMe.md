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

## Green Code - Best Practices

During Sprint 3, the following "Green Code" (Sustainable Software Engineering) practices were researched and applied:

1. **Efficiency and Resource Optimization**:
   - Used `openjdk:11-jre-slim` as the base image for Docker to reduce image size and resource consumption.
   - Implemented reactive programming concepts with `WebClient` and `Mono.zip` to handle concurrent I/O operations efficiently, reducing idle CPU time.
   - Minimal dependency usage to keep the application footprint small.

2. **Maintainability and Scalability**:
   - Microservices architecture allows scaling only the necessary components, saving energy by not over-provisioning resources.
   - Decoupled logic (Risk Assessment) from data management (Patient/Notes) ensures easier maintenance and targeted updates.

3. **Performance Optimization**:
   - Indexing on `patientId` in MongoDB and primary keys in PostgreSQL ensures faster data retrieval and less energy spent on disk I/O.
   - Trigger term search is performed in a case-insensitive manner once per assessment to minimize processing.

---

## How to Run the Microservices with Docker

- Ensure Docker and Docker Compose are installed.
- From the project root, run: `mvn clean package` (to build the JARs and generate Surefire/JaCoCo reports).
- Run: `docker-compose up --build`.
- To stop the application: `docker-compose down`.
- Access the frontend UI at `http://localhost:8082`.
- The Gateway remains at `http://localhost:8080`.

---

## Technical and Architectural Data Flow

This section describes the end-to-end flow of data across the system, from the User Interface down to the databases, through the Dockerized microservices.

### 1. External Entry (UI to Gateway)
*   **User Action**: A user interacts with the **Frontend** UI (e.g., clicks "View Risk").
*   **Frontend Request**: `PatientServiceImpl.java` or `RiskServiceImpl.java` uses `WebClient` to send an authenticated REST request to the **API Gateway**.
    *   *Path*: `http://localhost:8080/patients` or `http://localhost:8080/risk/{id}`
*   **Docker Network**: The request enters the Docker network via the `gateway` container, which maps port `8080`.

### 2. Routing and Orchestration (Gateway to Services)
*   **Routing**: The **Gateway** (`GatewayApplication.java`) identifies the route based on the URL prefix (e.g., `/patients/**` or `/risk/**`).
*   **Internal Call**: The Gateway forwards the request to the appropriate internal Docker service using the service name as the hostname (e.g., `http://patient-management:8081`).

### 3. Data Processing and Aggregation
*   **Structured Data**: The **Patient Management** service (`PatientController.java`) queries the **PostgreSQL** database via `PatientRepository.java` to fetch patient demographics.
*   **Unstructured Data**: The **Physician Notes** service (`NotesController.java`) queries **MongoDB** via `NoteRepository.java` to fetch medical notes.
*   **Risk Calculation (The "Brain")**:
    1.  The **Diabetes Risk** service (`RiskController.java`) receives a request for a patient.
    2.  It uses **Reactive WebClient** (`Mono.zip`) to call both the Patient and Notes services simultaneously through the Gateway.
    3.  The `RiskService.java` scans the notes for "trigger terms" (e.g., *Hemoglobin A1C*, *Cholesterol*) using optimized Regex logic.
    4.  It calculates the risk level (None, Borderline, In Danger, Early Onset) based on age, gender, and trigger count.

### 4. Persistence Layer (Databases)
*   **PostgreSQL**: Stores structured patient data (Name, DOB, Gender, Phone) in the `patients` table.
*   **MongoDB**: Stores unstructured clinical notes as JSON documents in the `notes` collection.
*   **Docker Volumes**: Data is persisted across container restarts using Docker volumes mapped to the local filesystem.

### 5. Final Response (Service to UI)
*   The calculated risk or patient data is sent back through the **Gateway**.
*   The **Frontend** receives the JSON, and Thymeleaf renders the updated HTML page for the user.

---

## WebClient and Reactive Communication

To ensure high performance and sustainability (Green Code), this project uses **Spring WebClient** for all inter-service communication.

### What is WebClient?
*   **Non-blocking I/O**: Unlike traditional clients, WebClient doesn't block threads while waiting for a response, allowing the server to handle more traffic with fewer resources.
*   **Reactive Programming**: It uses `Mono` and `Flux` to handle data streams asynchronously.

### Where to find it?
*   **Data Aggregation**: In `diabetes-risk/src/main/java/com/medilabo/risk/controller/RiskController.java`, WebClient fetches data from both the Patient and Notes services simultaneously via the Gateway.
*   **Frontend Client**: In `frontend/src/main/java/com/medilabo/frontend/service/PatientServiceImpl.java`, it acts as the bridge between the UI and the backend microservices.

---

## Docker Demo Guide: Orchestration & Connectivity

When demonstrating Docker to your assessor, focus on how the microservices are coordinated and how they communicate.

### 1. The Orchestration (docker-compose.yml)
*   **Show the File**: Open `docker-compose.yml` and point out the `services:` section.
*   **Key Point**: Explain that this file acts as the "Manager" for all 5 microservices. Instead of starting each one manually, Docker Compose ensures they all start together in the correct order using `depends_on`.

### 2. Service-to-Service Networking
*   **Hostname Resolution**: Show the `environment:` section in the `gateway` service:
    ```yaml
    PATIENT_SERVICE_URI: http://patient-management:8081
    ```
*   **Key Point**: Explain that inside the Docker network, services don't use IP addresses. They use **Service Names** as hostnames. For example, the `gateway` container can reach the `patient-management` container just by using its name. This makes the architecture portable and easy to manage.

### 3. Port Mapping (External vs. Internal)
*   **The Ports**: Point to `ports: - "8080:8080"`.
*   **Key Point**: Explain that the first number is the "Host Port" (what the browser sees) and the second is the "Container Port" (where the app is running). This allows you to run multiple apps on different ports without conflicts.

### 4. Hybrid Connectivity (Host.docker.internal)
*   **External Databases**: Point to the `SPRING_DATASOURCE_URL`:
    ```yaml
    jdbc:postgresql://host.docker.internal:5432/medilabo_db
    ```
*   **Key Point**: Explain that `host.docker.internal` is a special Docker DNS name that allows the container to talk back to your local computer (the host). This is how the containerized microservice connects to your local PostgreSQL or MongoDB during development.

### 5. Docker CLI Commands (Live Demo)
Show these commands in your terminal to prove the system is healthy:
*   `docker ps`: Shows all 5 services running as "Up".
*   `docker-compose logs -f [service-name]`: Shows live "Green Code" logs for a specific service (like `diabetes-risk`).

---

Thank you for reviewing this documentation.