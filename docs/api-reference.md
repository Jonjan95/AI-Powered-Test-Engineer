# API Reference

The local backend base URL is `http://localhost:8080`. All application routes use the `/api` prefix and JSON responses unless noted otherwise.

## Health

| Method | Path | Success | Description |
| --- | --- | --- | --- |
| `GET` | `/api/health` | `200 OK` | Return backend service status |

## Projects

| Method | Path | Success | Description |
| --- | --- | --- | --- |
| `POST` | `/api/projects` | `201 Created` | Create a project |
| `GET` | `/api/projects` | `200 OK` | List projects |
| `GET` | `/api/projects/{id}` | `200 OK` | Get a project |
| `PUT` | `/api/projects/{id}` | `200 OK` | Replace editable project fields |
| `DELETE` | `/api/projects/{id}` | `204 No Content` | Delete a project and dependent records |

Create and update body:

```json
{
  "name": "Customer portal",
  "description": "Tests for the customer-facing portal"
}
```

`name` is required, must not be blank, and has a maximum length of 255 characters. `description` is optional.

## User Stories

| Method | Path | Success | Description |
| --- | --- | --- | --- |
| `POST` | `/api/projects/{projectId}/user-stories` | `201 Created` | Create a user story in a project |
| `GET` | `/api/projects/{projectId}/user-stories` | `200 OK` | List a project's user stories |
| `GET` | `/api/user-stories/{id}` | `200 OK` | Get a user story |
| `PUT` | `/api/user-stories/{id}` | `200 OK` | Replace editable user-story fields |
| `DELETE` | `/api/user-stories/{id}` | `204 No Content` | Delete a user story and generated records |

Create and update body:

```json
{
  "title": "Customer signs in",
  "description": "As a customer, I want to sign in to my account.",
  "acceptanceCriteria": "Valid credentials open the account dashboard."
}
```

`title` is required, must not be blank, and has a maximum length of 255 characters. The other fields are optional.

## Generated Test Cases

| Method | Path | Success | Description |
| --- | --- | --- | --- |
| `POST` | `/api/user-stories/{userStoryId}/test-cases/generate` | `201 Created` | Generate test cases with OpenAI and store them |
| `GET` | `/api/user-stories/{userStoryId}/test-cases` | `200 OK` | List stored test cases for a user story |

Generation requires `OPENAI_API_KEY`. The request has no body. Each call creates another stored batch; it does not replace earlier test cases.

## Generated Playwright Tests

| Method | Path | Success | Description |
| --- | --- | --- | --- |
| `POST` | `/api/user-stories/{userStoryId}/playwright-tests/generate` | `201 Created` | Generate and store a Playwright TypeScript file |
| `GET` | `/api/user-stories/{userStoryId}/playwright-tests` | `200 OK` | List stored Playwright tests for a user story |
| `GET` | `/api/playwright-tests/{id}` | `200 OK` | Get one stored Playwright test |

Playwright generation requires `OPENAI_API_KEY` and at least one stored test case for the user story. If no test cases exist, the API returns `409 Conflict`.

The response contains `fileName` and `scriptContent`, but the application currently stores these values only. It does not create a physical `.spec.ts` file or execute the generated code.

## Common Error Responses

- `400 Bad Request` for request validation failures or malformed input
- `404 Not Found` when a project, user story, or Playwright test does not exist
- `409 Conflict` when Playwright generation is requested before test cases exist
- `502 Bad Gateway` when AI generation is unavailable, misconfigured, refused, or returns invalid structured output

Spring Boot currently supplies the error response shape. A custom versioned error contract has not yet been implemented.

## Browser Access

The backend allows browser requests to `/api/**` from the origin configured by `FRONTEND_ORIGIN`. The local default is `http://localhost:3000`. Supported CORS methods are `GET`, `POST`, `PUT`, `DELETE`, and `OPTIONS`.
