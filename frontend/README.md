# Frontend (Next.js + TypeScript + Tailwind)

This folder contains a minimal Next.js frontend that fetches backend health from `http://localhost:8080/api/health` and displays it.

Commands you should run locally (explanations below):

- `npm install` — install dependencies declared in `package.json`.
- `npm run dev` — start the Next.js development server on port 3000.

Notes:
- I used TypeScript and Tailwind CSS.
- The homepage at `/` will fetch `GET /api/health` and show `service` and `status` fields from the backend's JSON response.

