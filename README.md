# Hotel Haven

Hotel Haven is a local-first, college-level hotel booking application built with Java 21 and Spring Boot. It combines a premium natural-luxury landing page with real session authentication, a JPA/H2 data model, room browsing and authenticated bookings.

## Architecture
- **Backend:** Spring Boot 3.5, Spring MVC, Spring Security, Spring Data JPA, Bean Validation
- **Database:** H2 in-memory database for zero-setup local development
- **Frontend:** Server-rendered Thymeleaf pages + vanilla HTML/CSS/JavaScript
- **Authentication:** Spring Security form login + server-side HTTP session + BCrypt password hashing
- **API:** JSON endpoints under `/api`
- **Seed data:** Three rooms are created automatically on startup

## Domain model
`User` has many `Booking` records. Each `Booking` belongs to one `Room`. Booking totals are calculated server-side as room nightly price × number of nights.

## Security
Public routes are the landing page, static assets, room retrieval, login and registration. Dashboard and booking endpoints require authentication. Passwords are never stored in plaintext. Duplicate emails are rejected case-insensitively.

## API endpoints
| Method | Endpoint | Auth | Purpose |
|---|---|---|---|
| GET | `/api/rooms` | Public | List rooms |
| POST | `/api/register` | Public | Create an account |
| GET | `/api/me` | Required | Current user |
| GET | `/api/bookings` | Required | Current user's booking history |
| POST | `/api/bookings` | Required | Create a booking |
| POST | `/logout` | Required | End the session |

## Run locally
Requirements: Java 21 and Maven 3.9+.

```bash
mvn spring-boot:run
```

Open `http://localhost:8080`.

The H2 console is available locally at `http://localhost:8080/h2-console` using JDBC URL `jdbc:h2:mem:hotelhaven`, user `sa`, with an empty password.

## Test

```bash
mvn test
```

The test suite covers registration, duplicate registration, authentication/security, room retrieval, booking creation and booking retrieval.

## Notes / limitations
- H2 is configured as an in-memory database, so data resets when the application stops.
- The visual photography is represented by locally bundled SVG artwork so the project has no runtime dependency on image/CDN services.
- There is no payment processor by design; bookings are confirmed locally.
- This is a demonstration/college-level application rather than a production hotel inventory system; concurrency controls, email verification, password reset, payment reconciliation and production database migrations would be next steps.
