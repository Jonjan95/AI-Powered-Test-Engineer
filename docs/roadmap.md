# Roadmap

This roadmap distinguishes completed foundation work from the remaining product phases. A checked item is implemented in the current repository; an unchecked item is planned.

## Phase 1 - Project Foundation

- [x] Create the repository and project structure
- [x] Define the project vision, requirements, architecture, and roadmap
- [x] Add setup and development documentation
- [x] Establish the GitHub issue and review workflow

## Phase 2 - Backend and Database Foundation

- [x] Create the Java 21 Spring Boot application
- [x] Add the health endpoint
- [x] Configure PostgreSQL and Spring Data JPA
- [x] Manage the schema with Flyway
- [x] Add project CRUD
- [x] Add user-story CRUD scoped to projects
- [x] Add JUnit, Mockito, and MockMvc tests
- [x] Configure browser CORS for the frontend origin
- [ ] Add database migration and repository integration tests
- [ ] Add a consistent API error contract and API schema documentation

## Phase 3 - Frontend Foundation

- [x] Create the Next.js and TypeScript application
- [x] Configure Tailwind CSS
- [x] Connect the initial page to backend health
- [x] Install and configure the Playwright dependency
- [ ] Add application navigation and reusable UI structure
- [ ] Add project and user-story management screens
- [ ] Add generated-asset review screens

## Phase 4 - AI Test Generation

- [x] Add an OpenAI client abstraction and configuration
- [x] Generate structured test cases from user stories
- [x] Store and retrieve generated test cases
- [ ] Generate dedicated edge-case analysis
- [ ] Generate risk analysis
- [ ] Generate reusable test data

## Phase 5 - Playwright Code Generation

- [x] Generate structured Playwright TypeScript code from stored test cases
- [x] Validate and store generated filenames and script content
- [x] Retrieve stored Playwright tests through the API
- [ ] Display and review generated code in the frontend
- [ ] Export approved generated code to a runnable test workspace

## Phase 6 - Automated Test Execution

Generated Playwright tests are not currently executed.

- [ ] Execute approved Playwright tests safely
- [ ] Capture status, duration, output, and errors
- [ ] Store test execution history
- [ ] Display pass/fail results and reports

## Phase 7 - AI Failure Analysis

- [ ] Analyze failed test output
- [ ] Suggest possible root causes
- [ ] Recommend test or application improvements
- [ ] Display AI-generated explanations

## Phase 8 - Delivery and Cloud

- [ ] Add GitHub Actions quality checks
- [ ] Add application containerization
- [ ] Prepare production configuration and secret management
- [ ] Deploy the backend, frontend, and PostgreSQL infrastructure
- [ ] Add monitoring and operational documentation
