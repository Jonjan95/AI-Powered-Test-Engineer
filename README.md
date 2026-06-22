# AI-Powered Test Engineer

AI-Powered Test Engineer is a full-stack learning project exploring how artificial intelligence can support the software testing lifecycle. The current application can manage projects and user stories, generate structured test cases with the OpenAI API, and generate and store Playwright TypeScript test code.

> Generated Playwright tests are currently stored in PostgreSQL for review. The application does not yet write them to the frontend test suite or execute them.

## Implemented Features

- Spring Boot REST API with a health endpoint
- Project CRUD operations
- User story CRUD operations scoped to projects
- AI-generated test cases stored per user story
- AI-generated Playwright test code stored per user story
- PostgreSQL persistence managed with Flyway migrations
- Configurable frontend CORS origin
- Backend unit and controller tests with JUnit, Mockito, and MockMvc
- Next.js health-status page connected to the backend
- Playwright installed in the frontend with starter example tests

## Planned Features

- Project and user story management in the frontend
- Display and review of generated test cases and Playwright code
- Edge-case, risk, and test-data generation
- Execution of generated Playwright tests
- Test result history and reporting
- AI-assisted failure analysis
- Authentication, CI/CD, containerization, and Azure deployment

## Technology Stack

- **Frontend:** Next.js, React, TypeScript, Tailwind CSS
- **Backend:** Java 21, Spring Boot, Spring Data JPA
- **Database:** PostgreSQL 16, Flyway
- **AI:** OpenAI Responses API
- **Testing:** JUnit, Mockito, MockMvc, Playwright
- **Planned operations:** GitHub Actions, Docker, Microsoft Azure

## Current Architecture

```text
Browser
  |
  v
Next.js frontend (health page)
  |
  v
Spring Boot REST API
  |-- PostgreSQL (projects, user stories, generated assets)
  `-- OpenAI API (test-case and Playwright-code generation)
```

The backend uses feature-oriented packages with controllers, services, DTOs, entities, and Spring Data repositories. Flyway owns schema changes, while Hibernate validates that the entity mappings match the migrated schema.

## Project Structure

```text
AI-Powered-Test-Engineer/
|-- backend/       Spring Boot API, migrations, and backend tests
|-- docs/          Project and developer documentation
|-- frontend/      Next.js application and Playwright setup
|-- tests/         Reserved top-level test area
|-- .github/       GitHub configuration
`-- README.md
```

## Getting Started

For first-time installation and environment configuration, see [docs/setup.md](docs/setup.md).

For the normal development startup sequence and test commands, see [docs/quick-start.md](docs/quick-start.md).

## Documentation

- [Setup guide](docs/setup.md)
- [Quick start](docs/quick-start.md)
- [API reference](docs/api-reference.md)
- [Architecture](docs/architecture.md)
- [Database design](docs/database-design.md)
- [Requirements](docs/requirements.md)
- [Roadmap](docs/roadmap.md)
- [Project vision](docs/project-vision.md)
- [Future ideas](docs/future-ideas.md)

## Current Status

Issues #1-#9 established the project structure, backend and database foundations, project and user-story APIs, OpenAI integration, AI test-case generation, stored Playwright-code generation, frontend foundation, Playwright dependency setup, and frontend CORS configuration.

The backend foundation is functional. The frontend is still a minimal health-check page, and automated execution of generated tests has not been implemented.

## Author

Jonathan Jansson

Junior System Developer focused on software development, testing, automation, AI, and cloud technologies.
