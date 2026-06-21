# Quick Start

This guide explains how to start and work with the AI-Powered Test Engineer project.

---

# Daily Workflow

Before starting work:

```bash
git pull
```

Start required services:

1. Docker Desktop
2. PostgreSQL Container
3. Spring Boot Backend
4. Next.js Frontend

After finishing work:

```bash
git add .
git commit -m "description of changes"
git push
```

---

# Prerequisites

Installed locally:

* Java 21
* Node.js
* Docker Desktop
* Git
* VS Code

Verify installations:

```bash
java --version
node --version
docker --version
git --version
```

---

# Start PostgreSQL

Start Docker Desktop.

Verify Docker is running:

```bash
docker ps
```

Start PostgreSQL container:

```bash
docker start aipowered-postgres
```

Verify container is running:

```bash
docker ps
```

Expected container:

```text
aipowered-postgres
```

---

# Start Backend

Navigate to backend:

```bash
cd backend
```

## Windows

```powershell
.\mvnw.cmd spring-boot:run
```

## macOS / Linux

```bash
./mvnw spring-boot:run
```

Health endpoint:

```text
http://localhost:8080/api/health
```

Expected response:

```json
{
  "status": "UP",
  "service": "ai-powered-test-engineer-backend"
}
```

---

# Start Frontend

Open a new terminal.

Navigate to frontend:

```bash
cd frontend
```

Start Next.js:

```bash
npm run dev
```

Open:

```text
http://localhost:3000
```

---

# Running Tests

Backend:

```bash
cd backend
```

Windows:

```powershell
.\mvnw.cmd test
```

macOS / Linux:

```bash
./mvnw test
```

---

# Git Workflow

Check status:

```bash
git status
```

Fetch remote changes:

```bash
git fetch
```

See incoming commits:

```bash
git log HEAD..origin/main --oneline
```

Pull latest changes:

```bash
git pull
```

Commit work:

```bash
git add .
git commit -m "feat: description"
git push
```

---

# Docker Commands

View running containers:

```bash
docker ps
```

Start PostgreSQL:

```bash
docker start aipowered-postgres
```

Stop PostgreSQL:

```bash
docker stop aipowered-postgres
```

View logs:

```bash
docker logs aipowered-postgres
```

---

# First-Time Setup

Only required once.

Create PostgreSQL container:

```bash
docker run --name aipowered-postgres -e POSTGRES_USER=aipower -e POSTGRES_PASSWORD=123 -e POSTGRES_DB=aipowered -p 5432:5432 -d postgres:15
```

After this, use:

```bash
docker start aipowered-postgres
```

instead of creating a new container.

---

# Shutdown

Frontend:

```text
Ctrl + C
```

Backend:

```text
Ctrl + C
```

Database:

```bash
docker stop aipowered-postgres
```

---

# Project Architecture

```text
Frontend (Next.js)
        ↓
Backend (Spring Boot)
        ↓
PostgreSQL
        ↓
Flyway Migrations
```

---

# Current Development Process

1. Pick GitHub Issue
2. Implement in Learning Mode using Codex
3. Run tests
4. Verify manually
5. Commit
6. Push
7. Close Issue
8. Continue with next Issue

The goal is to understand every change before it is committed.
