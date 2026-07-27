# Crescer Juntos

REST API for managing family routines, children’s responsibilities, agreements, points, and rewards.

Crescer Juntos helps families organize children's daily activities through configurable tasks, period-based checklists, task approval, positive points, and rewards.

The project was developed as a backend portfolio project and is designed to evolve into a web or mobile application in the future.

## Objective

The main goal of Crescer Juntos is to make family routines clearer and more collaborative.

Responsible adults can configure tasks and rewards, while children can view their responsibilities, complete tasks, and follow their progress.

The project also establishes a foundation for future features such as authentication, notifications, negative points, configurable consequences, reports, and a web dashboard.

## Features

- Create and manage `Familia`;
- Create and manage `Crianca`;
- Create recurring `Tarefa`;
- Support daily, weekly, monthly, and custom periods;
- Generate task executions for a specific period;
- Track `ExecucaoTarefa`;
- Allow children to complete tasks;
- Allow responsible adults to approve or reject task executions;
- Assign positive points;
- Create and manage `Recompensa`;
- Request and manage `Resgate`;
- Track task and reward history;
- Handle business exceptions;
- Expose API documentation through OpenAPI/Swagger.

## Technologies

- Java 21;
- Spring Boot 4.1.0;
- Gradle;
- Spring Web;
- Spring Data JPA;
- PostgreSQL;
- Bean Validation;
- Lombok;
- OpenAPI/Swagger;
- JUnit;
- H2 for testing;
- Docker Compose;
- Railway.

## Architecture

The project follows Domain-Driven Design principles and a Clean Architecture-inspired structure.

```text
src/
├── main/
│   ├── java/
│   │   ├── application/
│   │   ├── domain/
│   │   └── infrastructure/
│   └── resources/
│
└── test/
    └── java/
```

### Main layers

#### `domain`

Contains the core business rules and domain models.

This layer includes:

- business entities;
- domain services;
- repository interfaces;
- domain exceptions;
- enums and business rules.

The domain is intentionally independent from JPA and web frameworks whenever possible.

#### `application`

Contains application-level orchestration, DTOs, mappers, and use cases.

This layer is responsible for:

- receiving application requests;
- converting external data into application models;
- orchestrating domain operations;
- returning response models.

#### `infrastructure`

Contains framework and technology-specific implementations.

This layer includes:

- REST controllers;
- JPA entities;
- JPA repositories;
- persistence mappers;
- repository implementations;
- global exception handling.

### Domain and persistence separation

The project separates domain models from JPA entities.

For example:

```text
domain/model/Familia.java
infrastructure/persistence/entity/FamiliaEntity.java
```

This decision keeps business rules independent from the persistence framework and makes the domain easier to test and evolve.

Entity mappers are responsible for converting between:

```text
Domain Model ↔ JPA Entity
```

Further architectural decisions are documented in:

```text
docs/adr/
```

## Domain Model

The main domain objects are:

```text
Familia
    ├── Crianca
    ├── Tarefa
    │       └── ExecucaoTarefa
    ├── Recompensa
    │       └── Resgate
```

The main business flow is:

```text
Responsável configura uma Tarefa
              ↓
Sistema disponibiliza a tarefa no período correto
              ↓
Criança conclui a tarefa
              ↓
Responsável aprova a execução
              ↓
Pontos são atribuídos
              ↓
Criança solicita uma Recompensa
              ↓
Responsável analisa o Resgate
```

The Portuguese domain terminology is intentional and is preserved in:

- Java class names;
- package names;
- API resources;
- enum values;
- domain concepts;
- endpoint names.

## Task Frequencies

The application supports the following task frequencies:

```text
DIARIA
SEMANAL
MENSAL
PERIODO_PERSONALIZADO
```

Examples:

```text
Escovar os dentes       → DIARIA
Arrumar o quarto        → SEMANAL
Organizar o material    → MENSAL
Ler três livros         → PERIODO_PERSONALIZADO
```

## Configuration

The application uses environment variables for database and runtime configuration.

Example:

```properties
PGHOST=localhost
PGPORT=5432
PGDATABASE=crescer_juntos
PGUSER=postgres
PGPASSWORD=postgres

PORT=8080
LOG_LEVEL=INFO
SQL_LOG_LEVEL=WARN
SHOW_SQL=false
```

Do not commit real credentials or production secrets to the repository.

## Running Locally

### Prerequisites

- Java 21;
- Gradle or the included Gradle Wrapper;
- Docker and Docker Compose;
- PostgreSQL, if running the database locally without Docker.

### Clone the repository

```bash
git clone https://github.com/YOUR_USERNAME/crescer-juntos.git
cd crescer-juntos
```

### Start the database

```bash
docker compose up -d
```

### Run the application

On Windows:

```powershell
.\gradlew.bat bootRun
```

On Linux or macOS:

```bash
./gradlew bootRun
```

### Run the tests

On Windows:

```powershell
.\gradlew.bat test
```

On Linux or macOS:

```bash
./gradlew test
```

## API Documentation

After starting the application, access the Swagger UI at:

```text
http://localhost:8080/swagger-ui/index.html
```

The OpenAPI specification is available at:

```text
http://localhost:8080/v3/api-docs
```

## Project Structure

```text
application/
├── dto/
│   ├── crianca/
│   ├── execucao/
│   ├── familia/
│   ├── mapper/
│   ├── recompensa/
│   ├── resgate/
│   └── tarefa/
└── usecase/

domain/
├── exception/
├── model/
├── repository/
└── service/

infrastructure/
├── persistence/
│   ├── entity/
│   ├── mapper/
│   └── repository/
└── web/

docs/
└── adr/
```

## Error Handling

The application uses centralized exception handling through a global exception handler.

Domain exceptions include:

```text
BusinessException
ResourceNotFoundException
PontosInsuficientesException
```

This approach provides consistent HTTP error responses and keeps error handling separated from business logic.

## Testing

The project uses:

- JUnit;
- Spring Boot Test;
- H2 for database tests;
- Gradle test tasks.

Run all tests with:

```bash
./gradlew test
```

The main testing priorities are:

- domain rules;
- task creation and updates;
- task execution status transitions;
- points assignment;
- reward redemption;
- insufficient points;
- resource-not-found scenarios;
- validation errors.

## Roadmap

### MVP

- [x] Domain definition;
- [x] Separation between domain models and JPA entities;
- [x] DTOs and mappers;
- [x] Business exception structure;
- [x] Gradle configuration;
- [ ] Complete `Familia` management;
- [ ] Complete `Crianca` management;
- [ ] Complete `Tarefa` management;
- [ ] Daily checklist;
- [ ] Weekly checklist;
- [ ] Monthly checklist;
- [ ] Custom periods;
- [ ] Task completion and approval;
- [ ] Points management;
- [ ] Reward redemption;
- [ ] Automated tests;
- [ ] Swagger documentation;
- [ ] Railway deployment.

### Future Improvements

- [ ] Authentication with Spring Security and JWT;
- [ ] Multiple responsible adults per family;
- [ ] Multiple children per family;
- [ ] Negative points;
- [ ] Configurable consequences;
- [ ] Notifications;
- [ ] Web dashboard;
- [ ] Mobile application;
- [ ] Progress reports;
- [ ] Gamification elements;
- [ ] Family activity history;
- [ ] Privacy and consent management.

## Privacy and Responsibility

The application may handle information related to family routines and children. Therefore, privacy and security must be considered from the beginning.

During development:

- use fictional data;
- do not commit credentials;
- do not expose children's profiles publicly;
- collect only necessary information;
- avoid photos and location data in the MVP;
- restrict access to authorized family members;
- keep secrets in environment variables;
- review LGPD requirements before commercial distribution.

Crescer Juntos is a family organization tool. It does not replace professional medical, psychological, pedagogical, or educational guidance.

## Status

In development.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.
