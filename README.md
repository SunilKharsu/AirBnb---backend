# AirBnb---backend


Backend service for an Airbnb-style booking platform, built with Spring Boot.

## Overview
`airBnbApp` provides APIs for:
- user authentication and profile management
- hotel and room management for hotel managers
- hotel browsing and search for guests
- booking lifecycle (reserve, pay, confirm, cancel)
- Stripe payment integration (checkout + webhook)
- dynamic pricing based on occupancy, urgency, holiday, and surge factor

## Tech Stack
- Java 21
- Spring Boot 3
- Spring Web, Spring Data JPA, Spring Security
- PostgreSQL (primary), MySQL connector included
- JWT authentication
- Stripe Java SDK
- Redis
- Swagger/OpenAPI (`springdoc-openapi`)
- Lombok, ModelMapper

## Core Modules
- **Auth & Security**: JWT login, refresh flow, role-based access
- **Hotel Management**: create/update hotels and rooms (manager role)
- **Inventory Management**: room availability by date
- **Booking System**: reservation, payment status, cancellation
- **Pricing Engine**: pluggable pricing strategies
- **Payments**: Stripe checkout and webhook confirmation

## API Base Path
`/api/v1`

## Main API Groups
- `POST /auth/signup`
- `POST /auth/login`
- `POST /auth/refresh`
- `GET /users/profile`
- `GET /users/myBookings`
- `GET /hotels/search`
- `GET /hotels/{hotelId}/info`
- `POST /bookings/init`
- `POST /bookings/{bookingId}/payments`
- `POST /webhook/payment`
- `/admin/**` endpoints for hotel managers

## Local Setup

### 1) Clone and enter project
```bash
git clone <your-repo-url>
cd airBnbApp
```

### 2) Configure environment
Update `src/main/resources/application.properties` (or better, use environment variables) for:
- database URL/username/password
- JWT secret
- Stripe secret key + webhook secret
- Redis host/port/password

### 3) Run application
```bash
./mvnw clean install
./mvnw spring-boot:run
```

### 4) Open API docs
`http://localhost:8080/api/v1/swagger-ui/index.html`

## Testing
```bash
./mvnw test
```

## Postman Collection
Import files from:
- `postman/airBnbApp.postman_collection.json`
- `postman/local.postman_environment.json`

## Notes
- This project currently uses `spring.jpa.hibernate.ddl-auto=create-drop` for development.
- Move all secrets/credentials out of source files before production deployment.

## Future Improvements
- increase test coverage
- harden validation and error handling
- fix known endpoint/repository inconsistencies
- production-ready config profiles and deployment setup
