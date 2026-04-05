# Sprint 1 - Patient Management Microservice, Gateway, and Frontend V1

## Sprint 1 Summary
This sprint focuses on developing the initial architecture of the application with the patient management microservice, a gateway for request routing, and the first version of the frontend UI. The goal is to ensure seamless communication between these components, with basic security, data persistence using PostgreSQL, and comprehensive functional testing. Global logging and test coverage have also been implemented.

---

## Entry Criteria

- Understanding of user stories and business context.
- Development environment setup (Java, Spring Boot, PostgreSQL).
- Initial configuration of the patient-management, gateway, and frontend microservices.
- Documentation and backlog review to define sprint tasks.

---

## Exit Criteria

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

## Sprint 1 Checklist - Mapping to Completed Tasks

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

## Functional Flow Description

1. **Patient Management Microservice**:  
   Manages patient data persistence using a PostgreSQL database. Exposes secured REST endpoints (GET, POST, PUT, DELETE) allowing full patient management. The microservice is launched via `PatientManagementApplication`.

2. **Spring Cloud Gateway**:  
   Acts as the single entry point to access different microservices. It routes incoming requests to the patient-management microservice securely and filters requests with Spring Security. Configuration is defined in `application.properties` and `SecurityConfig`.

3. **Frontend Microservice**:  
   A Spring Boot application using Thymeleaf that provides a simple UI to display and manipulate patient data. Communicates with the gateway to access the patient-management microservice. Supports full CRUD operations and uses `PatientController` to manage interactions.

4. **Logging and Testing**:  
   Global logging tracks all actions in the three microservices. Unit and integration tests are executed using Jacoco and Surefire to ensure code quality and coverage.

---

## How to Run the Microservices

- Start PostgreSQL and set up the database (check the `application.properties` in patient-management).
- Run `PatientManagementApplication` (patient-management module).
- Run the Gateway application.
- Run the Frontend application (`FrontendApplication`).
- Access REST endpoints via REST tools or through the frontend UI.
- Check the logs to trace all performed actions.
- Execute unit tests using Maven/Gradle commands.

---

Thank you for reviewing this documentation.