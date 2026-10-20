# DevLink

A REST API for developer profile pages, in the spirit of a link-in-bio page for engineers. A developer signs up, fills in a profile with a header, image, social links, external links and projects, and shares one public URL. Visitors see the profile, and its owner sees how many times it was viewed.

This repository holds the Spring Boot backend.

## Features

- Sign up and log in with JWT authentication; passwords hashed with BCrypt.
- A profile with a header, an image, social links (GitHub, LinkedIn and others) and external links.
- Projects on the profile: add, update and delete.
- Show or hide the whole profile.
- A public profile endpoint by username, with a view counter and a view count for the owner.
- Update or delete the user account.
- Request validation and one global exception handler, so errors come back in a consistent shape.
- OpenAPI docs and Swagger UI.

## API overview

| Area | Endpoints |
| --- | --- |
| Auth (public) | `POST /api/auth/register`, `POST /api/auth/login` |
| Public profile | `GET /api/public/{username}`, `PATCH /api/public/increment-views/{profileId}` |
| Profile (JWT) | `PUT /api/profile/update-header`, `PUT /api/profile/update-image`, `PUT /api/profile/social-links`, `PUT /api/profile/external-links`, `PUT /api/profile/hide-profile`, `PUT /api/profile/show-profile`, `GET /api/profile/get-views` |
| Projects (JWT) | `POST /api/profile/projects`, `PUT /api/profile/projects`, `DELETE /api/profile/projects/{projectId}` |
| Account (JWT) | `PUT /api/user`, `DELETE /api/user` |

Authenticated requests send `Authorization: Bearer <token>`. The full contract is in Swagger UI once the app runs.

## Architecture

- **Layers:** controllers handle HTTP, services hold the business rules, Spring Data JPA repositories talk to PostgreSQL. DTOs keep entities out of the API.
- **Entities:** `User`, `Profile`, `Project`, `SocialLink`, `ExternalLink`.
- **Security:** a stateless Spring Security filter chain with a JWT filter; auth and public routes are open, everything else needs a valid token. Tokens are signed with an HMAC key from the `JWT_SECRET` environment variable.
- **Errors:** a `@ControllerAdvice` maps domain exceptions (for example wrong credentials) to HTTP responses.

## Stack

Java 17, Spring Boot 3.5, Spring Security, Spring Data JPA, Bean Validation, PostgreSQL, jjwt, springdoc-openapi, Lombok, JUnit 5 and Mockito, Maven.

## Run locally

Requirements: Java 17 or later and a PostgreSQL database called `devlink`.

```bash
cd backend-spring-boot/DevLink
export DB_URL=jdbc:postgresql://localhost:5432/devlink
export DB_USERNAME=postgres
export DB_PASSWORD=your-password
export JWT_SECRET=$(openssl rand -base64 48)
./mvnw spring-boot:run
```

The API runs on `http://localhost:8080` and Swagger UI on `http://localhost:8080/swagger-ui.html`. `backend-spring-boot/DevLink/.env.example` lists every variable; secrets are read from the environment and never committed.

## Tests

Unit tests for the services (auth, profile, project and user) with JUnit 5 and Mockito:

```bash
./mvnw test
```

## Next steps

- Count views per visitor rather than per request.
- Integration tests for the security filter chain and controllers.
- A frontend for editing and viewing profiles.
