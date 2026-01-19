# Score App — Credit Scoring Microservice Platform

A backend platform for credit scoring and risk assessment, developed as a diploma project and production-like system.  
Implements a microservice architecture with authentication, scoring logic, integrations, and analytics.

---

## Overview

Score App is a Java-based backend system designed to evaluate creditworthiness and support decision-making in financial products.  
The platform processes user data, calculates scoring metrics, and exposes secure REST APIs for integration with external services.

Key goals:
- Modular and scalable architecture
- Secure authentication and authorization
- Production-ready backend practices
- Clear separation of business logic and infrastructure

---

## Architecture

The system follows a microservice-oriented architecture and includes:

- **Auth Service** — JWT-based authentication and role-based access control
- **Scoring Service** — business logic for score calculation
- **User Service** — user profile and data management
- **Analytics Service** — scoring statistics and reporting
- **Database layer** — PostgreSQL for persistent storage
- **Infrastructure** — Docker + Docker Compose

Architecture highlights:
- Layered architecture (controller → service → repository)
- DTO-based communication
- Centralized exception handling
- OpenAPI / Swagger documentation

---

## Tech Stack

**Backend:**
- Java 17
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA
- Hibernate

**Databases:**
- PostgreSQL

**Infrastructure & DevOps:**
- Docker
- Docker Compose
- GitHub Actions (CI)

**Documentation:**
- OpenAPI / Swagger

---

## Security

- JWT-based authentication
- Role-based access control (RBAC)
- Secure password storage
- Protected endpoints using Spring Security filters

---

## Features

- User registration and authentication
- Credit scoring calculation
- Risk classification
- Secure REST API endpoints
- Analytics and reporting
- Containerized deployment
- Swagger-based API documentation

---

## Testing

The project includes:
- Unit tests for business logic
- Integration tests for REST endpoints and persistence layer
- Testcontainers / embedded DB (if applicable)

---

## Running the Project

### Prerequisites
- Java 17+
- Docker & Docker Compose
- Maven

### Steps

```bash
git clone https://github.com/Andrew97422/score-app.git
cd score-app
docker-compose up --build
```

The application will be available at:

API: ```http://localhost:8080```

### API Documentation
Swagger UI is available at:

```http://localhost:8080/swagger-ui.html```

## Project Background

This project was developed as a diploma project and reflects real-world backend engineering practices in financial systems:
- Focus on clean architecture
- Emphasis on security and scalability
- Production-oriented design decisions

## Author
Andrew Nosov
Java Backend Developer
GitHub: ```https://github.com/Andrew97422```

Email: ```nosoff.4ndr@yandex.ru```
