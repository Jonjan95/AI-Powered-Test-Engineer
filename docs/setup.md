# Setup Guide

## Overview

This document describes how to set up the AI-Powered Test Engineer development environment.

The project is currently in the planning and architecture phase, therefore some setup steps will be added as the project evolves.

---

## Development Environment

Recommended tools:

### Git

Used for version control.

Recommended version:

* Git 2.40+

### Java

Backend development.

Recommended version:

* Java 21 LTS

Verify installation:

```bash
java --version
```

### Maven

Backend dependency management.

Verify installation:

```bash
mvn --version
```

### Node.js

Frontend development.

Recommended version:

* Node.js 22 LTS

Verify installation:

```bash
node --version
npm --version
```

### PostgreSQL

Database system.

Recommended version:

* PostgreSQL 16+

Verify installation:

```bash
psql --version
```

### Visual Studio Code

Recommended editor.

Suggested extensions:

* Java Extension Pack
* Spring Boot Extension Pack
* ESLint
* Prettier
* Playwright Test for VS Code
* GitHub Copilot
* GitHub Pull Requests and Issues

---

## Clone Repository

Clone the repository:

```bash
git clone https://github.com/Jonjan95/AI-Powered-Test-Engineer.git
```

Navigate to the project:

```bash
cd AI-Powered-Test-Engineer
```

Open in VS Code:

```bash
code .
```

---

## Project Structure

```text
AI-Powered-Test-Engineer/
├── .github/
│   └── workflows/
├── backend/
├── docs/
├── frontend/
├── tests/
├── .gitignore
├── LICENSE
└── README.md
```

---

## Backend Setup

Planned location:

```text
backend/
```

Planned technologies:

* Java
* Spring Boot
* Maven
* PostgreSQL

Setup instructions will be added during Phase 2.

---

## Frontend Setup

Planned location:

```text
frontend/
```

Planned technologies:

* Next.js
* TypeScript
* Tailwind CSS

Setup instructions will be added during Phase 3.

---

## Testing Setup

Planned testing technologies:

### Backend

* JUnit
* Mockito

### Frontend

* Playwright

Setup instructions will be added during later phases.

---

## Environment Variables

Secrets must never be committed to Git.

Examples:

```env
OPENAI_API_KEY=
DATABASE_URL=
DATABASE_USERNAME=
DATABASE_PASSWORD=
```

Environment files should be ignored by Git using:

```text
.env
.env.local
```

---

## Cloud Setup

Planned cloud platform:

* Microsoft Azure

Future deployment targets:

* Azure App Service
* Azure PostgreSQL
* Azure OpenAI
* Azure Monitor

Deployment instructions will be added in later phases.

---

## Development Workflow

Development follows:

1. Create GitHub Issue
2. Create Feature Branch
3. Implement Feature
4. Commit Changes
5. Open Pull Request
6. Review Changes
7. Merge to Main

AI-generated code should always be reviewed before being committed.

---

## Current Status

Current phase:

Phase 1 - Project Foundation

Focus areas:

* Documentation
* Architecture
* Requirements
* Development workflow
* GitHub setup

No application code has been implemented yet.
