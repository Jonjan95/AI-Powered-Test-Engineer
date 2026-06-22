# Quick Start

This is the daily command guide for the local development environment. Complete [setup.md](setup.md) first if the repository or PostgreSQL container has not been prepared.

## 1. Start PostgreSQL

Start Docker Desktop, then start the existing PostgreSQL 16 container:

```bash
docker start aipowered-postgres
docker ps
```

If the container does not exist yet, create it once:

```bash
docker run --name aipowered-postgres -e POSTGRES_USER=aipower -e POSTGRES_PASSWORD=123 -e POSTGRES_DB=aipowered -p 5432:5432 -d postgres:16
```

Useful database commands:

```bash
docker logs aipowered-postgres
docker stop aipowered-postgres
```

## 2. Set the OpenAI Key When Needed

CRUD and health requests do not require an OpenAI key. Generation requests do.

PowerShell:

```powershell
$env:OPENAI_API_KEY="your-api-key"
```

macOS or Linux:

```bash
export OPENAI_API_KEY="your-api-key"
```

See [setup.md](setup.md#environment-variables) for all supported variables.

## 3. Start the Backend

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

Verify `http://localhost:8080/api/health` returns:

```json
{
  "status": "UP",
  "service": "ai-powered-test-engineer-backend"
}
```

## 4. Start the Frontend

Open a second terminal:

```bash
cd frontend
npm install
npm run dev
```

Open `http://localhost:3000`.

## 5. Run Backend Tests

Windows PowerShell:

```powershell
cd backend
.\mvnw.cmd test
```

macOS or Linux:

```bash
cd backend
./mvnw test
```

## 6. Run Playwright Tests

Install browser binaries once:

```bash
cd frontend
npx playwright install
```

Run the suite:

```bash
npx playwright test
```

Open the HTML report after a run:

```bash
npx playwright show-report
```

The current Playwright files are starter examples against `playwright.dev`; they do not exercise this application. Playwright code generated through the backend API is stored in PostgreSQL and is not automatically executed.

## Daily Git Checks

Before working:

```bash
git status
git pull
```

After working, inspect changes before choosing what to stage and commit:

```bash
git status
git diff
```

## Shutdown

Stop frontend and backend processes with `Ctrl+C`, then stop the database:

```bash
docker stop aipowered-postgres
```

## Current Request Flow

```text
Next.js health page
        |
        v
Spring Boot REST API
        |-- PostgreSQL through Spring Data JPA and Flyway
        `-- OpenAI API for generation requests
```

For available routes and generation prerequisites, see [api-reference.md](api-reference.md).
