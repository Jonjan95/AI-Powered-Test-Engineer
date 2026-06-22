# Database Design

## Overview

PostgreSQL 16 is the supported local database version. Flyway migrations in `backend/src/main/resources/db/migration` define the schema, and Hibernate validates the JPA mappings at application startup.

This document separates tables that exist now from entities planned for later phases.

## Implemented Relationship Model

```text
projects
  `-- user_stories
        |-- test_cases
        `-- playwright_tests

test_record (legacy foundation scaffold; independent)
```

Deleting a project cascades to its user stories. Deleting a user story cascades to its test cases and Playwright tests.

## Implemented Tables

### `projects`

Stores a testing project.

| Column | Type | Constraints |
| --- | --- | --- |
| `id` | UUID | Primary key |
| `name` | VARCHAR(255) | Not null |
| `description` | TEXT | Nullable |
| `created_at` | TIMESTAMP WITH TIME ZONE | Not null |
| `updated_at` | TIMESTAMP WITH TIME ZONE | Not null |

### `user_stories`

Stores a requirement belonging to a project.

| Column | Type | Constraints |
| --- | --- | --- |
| `id` | UUID | Primary key |
| `project_id` | UUID | Not null; foreign key to `projects.id` with cascade delete |
| `title` | VARCHAR(255) | Not null |
| `description` | TEXT | Nullable |
| `acceptance_criteria` | TEXT | Nullable |
| `created_at` | TIMESTAMP WITH TIME ZONE | Not null |

An index exists on `project_id`.

### `test_cases`

Stores AI-generated test cases belonging to a user story.

| Column | Type | Constraints |
| --- | --- | --- |
| `id` | UUID | Primary key |
| `user_story_id` | UUID | Not null; foreign key to `user_stories.id` with cascade delete |
| `title` | VARCHAR(255) | Not null |
| `preconditions` | TEXT | Not null |
| `test_steps` | TEXT | Not null |
| `expected_result` | TEXT | Not null |
| `test_type` | VARCHAR(100) | Not null |
| `created_at` | TIMESTAMP WITH TIME ZONE | Not null |

An index exists on `user_story_id`. Repeated generation requests append records; the schema does not currently track generation batches or versions.

### `playwright_tests`

Stores AI-generated Playwright source code belonging to a user story.

| Column | Type | Constraints |
| --- | --- | --- |
| `id` | UUID | Primary key |
| `user_story_id` | UUID | Not null; foreign key to `user_stories.id` with cascade delete |
| `file_name` | VARCHAR(255) | Not null |
| `script_content` | TEXT | Not null |
| `created_at` | TIMESTAMP WITH TIME ZONE | Not null |

An index exists on `user_story_id`.

These rows contain generated code only. There is no implemented execution table, execution status, report, or link to a physical `.spec.ts` file.

### `test_record`

The first migration created `test_record` as a database-foundation scaffold:

| Column | Type | Constraints |
| --- | --- | --- |
| `id` | BIGSERIAL | Primary key |
| `name` | VARCHAR(255) | Not null |
| `result` | TEXT | Nullable |
| `created_at` | TIMESTAMP WITH TIME ZONE | Not null; defaults to current time |

The matching JPA entity and repository exist, but no current service or API uses them. It should not be confused with the planned execution-result model. Removing it would require a new forward-only Flyway migration rather than editing the existing `V1` migration.

## Planned Entities

The following concepts are not implemented in the current schema:

### Test execution

Would record a run of an approved Playwright test, including status, duration, output, error details, timestamps, and runner metadata.

### Edge-case analysis

Would store dedicated edge cases if they need an independent lifecycle instead of remaining part of generated test cases.

### Risk analysis

Would store identified risks, categories, severity, and explanations for a user story.

### Generated test data

Would store reusable generated datasets with appropriate handling for sensitive values.

### Identity and collaboration

Possible later entities include users, teams, workspaces, roles, audit logs, provider configuration, and prompt templates.

## Migration Rules

- Add schema changes through a new versioned Flyway migration.
- Do not modify a migration that has already been applied to a shared database.
- Keep foreign-key columns indexed when they are used for lookup.
- Keep JPA entities aligned with migrations because Hibernate runs in validation mode.
- Use timestamp-with-time-zone values consistently and store application timestamps in UTC.
