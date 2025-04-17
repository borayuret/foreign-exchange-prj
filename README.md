# Foreign Exchange API

A self-contained Spring Boot application for real-time currency exchange and conversion.  
Developed as part of the OpenPayd Java Developer Case Study.

---

## Features

- **Exchange Rate Lookup:** Get real-time exchange rates using Fixer.io or currencylayer.com
- **Currency Conversion:** Convert any amount from one currency to another
- **Transaction History:** Retrieve past conversions with filters
- **Bulk Conversion:** Upload CSV file and get multiple conversions in one call
- **Caching:** Redis cache integration to reduce external API load
- **Error Handling:** Meaningful error messages with standard error codes
- **Swagger UI:** Interactive API documentation
- **Dockerized:** Easily runnable in any environment with Docker

---

## Technologies

- Java 21
- Spring Boot 3.2.5
- Maven
- H2 In-Memory Database
- Redis (Dockerized)
- Spring Data JPA
- Spring Validation
- SpringDoc OpenAPI (Swagger)
- JUnit 5 & Mockito
- Docker & Docker Compose

---

## Endpoints Overview

| Endpoint                    | Method | Description                                   |
|----------------------------|--------|-----------------------------------------------|
| `/api/v1/rate`             | GET    | Get exchange rate between two currencies      |
| `/api/v1/convert`          | POST   | Convert an amount and get a transaction ID    |
| `/api/v1/history`          | GET    | Retrieve past conversions (filterable)        |
| `/api/v1/convert/bulk`     | POST   | Bulk currency conversion via CSV upload       |

Full Swagger Documentation:  
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## Run with Docker

Make sure Docker and Docker Compose are installed. Then run:

```bash
docker-compose up --build
