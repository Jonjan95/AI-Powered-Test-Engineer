# Database Design

## Overview

The database is responsible for storing projects, requirements, generated test assets, Playwright scripts, and execution results.

PostgreSQL will be used as the primary database.

---

# Entity Relationship Overview

```text
Project
 |
 +-- UserStory
        |
        +-- TestCase
        |
        +-- EdgeCase
        |
        +-- RiskAnalysis
        |
        +-- PlaywrightTest
                |
                +-- TestExecution
```

---

# Project

Represents a testing project.

## Fields

| Field       | Type      |
| ----------- | --------- |
| id          | UUID      |
| name        | String    |
| description | String    |
| createdAt   | Timestamp |
| updatedAt   | Timestamp |

---

# UserStory

Represents a user story or requirement.

## Fields

| Field              | Type      |
| ------------------ | --------- |
| id                 | UUID      |
| projectId          | UUID      |
| title              | String    |
| description        | Text      |
| acceptanceCriteria | Text      |
| createdAt          | Timestamp |

## Relationship

Many UserStories belong to one Project.

---

# TestCase

Represents AI-generated test cases.

## Fields

| Field          | Type      |
| -------------- | --------- |
| id             | UUID      |
| userStoryId    | UUID      |
| title          | String    |
| preconditions  | Text      |
| testSteps      | Text      |
| expectedResult | Text      |
| testType       | String    |
| createdAt      | Timestamp |

## Relationship

Many TestCases belong to one UserStory.

---

# EdgeCase

Represents AI-generated edge cases.

## Fields

| Field       | Type      |
| ----------- | --------- |
| id          | UUID      |
| userStoryId | UUID      |
| description | Text      |
| createdAt   | Timestamp |

## Relationship

Many EdgeCases belong to one UserStory.

---

# RiskAnalysis

Represents AI-generated risk assessments.

## Fields

| Field       | Type      |
| ----------- | --------- |
| id          | UUID      |
| userStoryId | UUID      |
| riskLevel   | String    |
| description | Text      |
| createdAt   | Timestamp |

## Relationship

Many RiskAnalysis entries belong to one UserStory.

---

# PlaywrightTest

Represents generated Playwright test scripts.

## Fields

| Field         | Type      |
| ------------- | --------- |
| id            | UUID      |
| userStoryId   | UUID      |
| fileName      | String    |
| scriptContent | Text      |
| createdAt     | Timestamp |

## Relationship

Many PlaywrightTests belong to one UserStory.

---

# TestExecution

Represents test execution results.

## Fields

| Field            | Type      |
| ---------------- | --------- |
| id               | UUID      |
| playwrightTestId | UUID      |
| status           | String    |
| executionTime    | Integer   |
| errorMessage     | Text      |
| executedAt       | Timestamp |

## Relationship

Many TestExecutions belong to one PlaywrightTest.

---

# Future Entities

The following entities may be added later:

* User
* Team
* Workspace
* APIKey
* AIConversation
* PromptTemplate
* AuditLog

These are intentionally excluded from Version 1 to keep the project manageable.
