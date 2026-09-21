# SpendWise — Spring Boot Backend

Complete modular-monolith backend for the SpendWise Personal Finance & Budget Management Platform.

## Requirements
- Java 21
- Maven 3.9+
- PostgreSQL 16+ (or Docker)

## Run with Docker
```bash
mvn clean package -DskipTests
docker compose up --build
```

## Run locally
Create PostgreSQL database `spendwise`, user `spendwise`, password `spendwise`, then:
```bash
mvn spring-boot:run
```

Swagger: http://localhost:8080/swagger-ui.html
Health: http://localhost:8080/actuator/health

## Authentication
1. POST `/api/auth/register`
2. POST `/api/auth/login`
3. Copy `token` from the response.
4. Send `Authorization: Bearer <token>` to protected endpoints.

## API groups
- `/api/auth`
- `/api/users`
- `/api/categories`
- `/api/expenses`
- `/api/income`
- `/api/budgets`
- `/api/goals`
- `/api/recurring-expenses`
- `/api/notifications`
- `/api/analytics`

## Architecture
Controller → DTO → Service → Repository → JPA Entity → PostgreSQL

JWT is stateless. BCrypt is used for passwords. Flyway owns database schema. Recurring expenses are processed by a daily Spring Scheduler.
