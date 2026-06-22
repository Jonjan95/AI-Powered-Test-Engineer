# Architecture

## Overview

AI-Powered Test Engineer currently consists of a Next.js frontend, a Spring Boot REST API, PostgreSQL persistence, and an OpenAI integration. The implemented system manages projects and user stories and stores AI-generated test cases and Playwright code.

Automated execution, result storage, reporting, and AI failure analysis remain planned capabilities.

## Implemented Architecture

```text
User's browser
      |
      v
Next.js frontend :3000
      |
      | HTTP/JSON to /api/**
      v
Spring Boot backend :8080
      |
      |-- Controllers -> Services -> Spring Data repositories
      |                           |
      |                           v
      |                     PostgreSQL 16
      |                     (Flyway schema)
      |
      `-- AI service -> OpenAI Responses API
                        (generation requests)
```

The frontend origin permitted by backend CORS is configured with `FRONTEND_ORIGIN`. The local default is `http://localhost:3000`.

## Frontend

The frontend uses Next.js, React, TypeScript, and Tailwind CSS. Its current responsibility is limited to displaying backend health information.

Planned frontend responsibilities include:

- Project and user-story management
- Starting generation requests
- Reviewing generated test cases and Playwright code
- Displaying future execution results and failure analysis

Business rules and persistence remain backend responsibilities.

## Backend

The backend uses feature-oriented packages:

- `health` exposes service status.
- `project` manages project CRUD.
- `userstory` manages project-scoped user stories.
- `testcase` generates and retrieves test cases.
- `playwright` generates and retrieves Playwright code.
- `ai` isolates OpenAI configuration, requests, and structured response parsing.
- `model` and `repository` provide JPA persistence.
- `config` contains cross-origin configuration.

Controllers handle HTTP mapping and validation. Services coordinate business operations and transactions. Request and response records keep API models separate from JPA entities.

## Database

PostgreSQL stores projects, user stories, generated test cases, and generated Playwright tests. Flyway applies versioned migrations, and Hibernate validates entity-to-schema compatibility at startup.

Foreign keys use cascading deletion so deleting a project removes its user stories and their generated records. See [database-design.md](database-design.md) for the implemented schema.

## AI Generation

The backend sends user-story data to the OpenAI Responses API and requests strict structured JSON. Generated test cases are validated through the response schema and persisted. Playwright generation uses the user story plus all stored test cases and validates the returned filename before persistence.

Generation requires `OPENAI_API_KEY`. The application treats user-supplied requirements as data in its AI instructions to reduce prompt-injection risk.

## Playwright: Generation Versus Execution

Two separate Playwright-related capabilities must not be confused:

1. **Implemented:** The backend generates a filename and TypeScript `scriptContent`, then stores both in PostgreSQL.
2. **Not implemented:** The application does not write that content to disk, approve it, sandbox it, run it, or store execution results.

The frontend repository also contains an independent Playwright starter configuration and example tests against `playwright.dev`. Those examples are not connected to backend-generated tests.

## Current Generation Flow

```text
Create project
  -> Create user story
  -> Generate and store test cases through OpenAI
  -> Generate and store Playwright code through OpenAI
  -> Retrieve stored assets through the API
```

## Planned Execution Architecture

A future execution layer will require an explicit approval boundary and isolated runner:

```text
Stored Playwright code
  -> Review and approval
  -> Isolated Playwright runner
  -> Execution results
  -> PostgreSQL history
  -> Optional AI failure analysis
```

Generated code must not be executed directly inside the Spring Boot API process. Runner isolation, target allow-listing, resource limits, secret handling, and auditability should be designed before execution is added.

## Future Architecture Considerations

- Authentication and authorization
- API versioning and a stable error contract
- Pagination for collection endpoints
- Background jobs for long-running AI and test operations
- Multiple AI providers
- CI/CD and container deployment
- Azure hosting, managed PostgreSQL, and monitoring
