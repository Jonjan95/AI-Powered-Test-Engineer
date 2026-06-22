# Setup Guide

This guide describes the first-time local setup for the AI-Powered Test Engineer project. For the shorter daily startup sequence, see [quick-start.md](quick-start.md).

## Prerequisites

Install the following tools:

- Git 2.40 or later
- Java 21 LTS
- Node.js 22 LTS with npm
- Docker Desktop
- Visual Studio Code or another editor

The project uses the included Maven Wrapper, so a separate Maven installation is optional.

Verify the command-line tools:

```bash
git --version
java --version
node --version
npm --version
docker --version
```

## Clone the Repository

```bash
git clone https://github.com/Jonjan95/AI-Powered-Test-Engineer.git
cd AI-Powered-Test-Engineer
```

## Project Structure

```text
AI-Powered-Test-Engineer/
|-- backend/       Spring Boot application and Flyway migrations
|-- docs/          Project documentation
|-- frontend/      Next.js application and Playwright tests
|-- tests/         Reserved top-level test area
`-- README.md
```

## PostgreSQL with Docker

Local development is standardized on PostgreSQL 16.

Create the container once:

```bash
docker run --name aipowered-postgres -e POSTGRES_USER=aipower -e POSTGRES_PASSWORD=123 -e POSTGRES_DB=aipowered -p 5432:5432 -d postgres:16
```

On later days, start the existing container:

```bash
docker start aipowered-postgres
```

Verify it is running:

```bash
docker ps
docker logs aipowered-postgres
```

The username and password above are local development defaults only. Use environment-specific secrets outside local development.

## Backend Setup

The backend defaults match the Docker container above. Start it from `backend/`.

Windows PowerShell:

```powershell
cd backend
.\mvnw.cmd spring-boot:run
```

macOS or Linux:

```bash
cd backend
./mvnw spring-boot:run
```

Flyway applies pending migrations at startup. Hibernate uses `ddl-auto: validate`, so it validates the migrated schema instead of creating it.

Verify the backend at `http://localhost:8080/api/health`.

## Frontend Setup

Open another terminal and install dependencies:

```bash
cd frontend
npm install
npm run dev
```

Open `http://localhost:3000`. The current page requests the backend health endpoint at `http://localhost:8080/api/health`.

## Playwright Setup

Install the browser binaries after installing frontend dependencies:

```bash
cd frontend
npx playwright install
```

Run the current Playwright suite with:

```bash
npx playwright test
```

The current suite contains Playwright starter examples that visit `playwright.dev`. It is not yet an end-to-end suite for this application. Generated Playwright code from the backend is stored in PostgreSQL; it is not automatically copied into `frontend/tests` or executed.

## Environment Variables

The backend reads these variables:

| Variable | Required | Local default | Purpose |
| --- | --- | --- | --- |
| `DATABASE_URL` | No | `jdbc:postgresql://localhost:5432/aipowered` | JDBC connection URL |
| `DATABASE_USERNAME` | No | `aipower` | PostgreSQL username |
| `DATABASE_PASSWORD` | No | `123` | PostgreSQL password for local development |
| `OPENAI_API_KEY` | Yes for generation | Empty | Authenticates OpenAI generation requests |
| `OPENAI_MODEL` | No | `gpt-5.5` | Selects the model used for generation |
| `FRONTEND_ORIGIN` | No | `http://localhost:3000` | Single browser origin allowed to call `/api/**` |

The health and CRUD endpoints work without an OpenAI key. Test-case and Playwright-code generation require `OPENAI_API_KEY`.

PowerShell example for the current terminal:

```powershell
$env:OPENAI_API_KEY="your-api-key"
$env:OPENAI_MODEL="gpt-5.5"
$env:FRONTEND_ORIGIN="http://localhost:3000"
```

bash example for the current terminal:

```bash
export OPENAI_API_KEY="your-api-key"
export OPENAI_MODEL="gpt-5.5"
export FRONTEND_ORIGIN="http://localhost:3000"
```

Never commit real secrets. `.env` and `.env.*` files are ignored by the repository, but Spring Boot does not automatically load a root `.env` file; export variables in the shell or configure them in your run environment.

## Running Backend Tests

Windows:

```powershell
cd backend
.\mvnw.cmd test
```

macOS or Linux:

```bash
cd backend
./mvnw test
```

## Useful Endpoints

- Frontend: `http://localhost:3000`
- Backend health: `http://localhost:8080/api/health`
- Backend API base: `http://localhost:8080/api`

See [api-reference.md](api-reference.md) for the complete implemented API.

## Shutdown

Stop the frontend and backend with `Ctrl+C` in their terminals. Stop PostgreSQL with:

```bash
docker stop aipowered-postgres
```

## Troubleshooting

- If the backend cannot connect, confirm Docker Desktop and `aipowered-postgres` are running.
- If port 5432 is busy, stop the other PostgreSQL service or configure a different port and `DATABASE_URL`.
- If generation returns an upstream error, confirm `OPENAI_API_KEY` is set in the backend process.
- If browser API requests fail because of CORS, confirm `FRONTEND_ORIGIN` exactly matches the frontend origin, including scheme and port.
