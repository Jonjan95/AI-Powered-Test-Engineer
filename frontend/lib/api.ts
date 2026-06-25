import type {
  CreateProjectRequest,
  CreateUserStoryRequest,
  Project,
  UserStory,
} from '../types/api'

const API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL ?? 'http://localhost:8080'

async function request<T>(path: string, options: RequestInit = {}): Promise<T> {
  const response = await fetch(`${API_BASE_URL}${path}`, {
    ...options,
    headers: {
      'Content-Type': 'application/json',
      ...options.headers,
    },
  })

  if (!response.ok) {
    const message = await response.text()
    throw new Error(message || `${response.status} ${response.statusText}`)
  }

  return response.json() as Promise<T>
}

export function getProjects(): Promise<Project[]> {
  return request<Project[]>('/api/projects')
}

export function createProject(project: CreateProjectRequest): Promise<Project> {
  return request<Project>('/api/projects', {
    method: 'POST',
    body: JSON.stringify(project),
  })
}

export function getUserStories(projectId: string): Promise<UserStory[]> {
  return request<UserStory[]>(`/api/projects/${projectId}/user-stories`)
}

export function createUserStory(
  projectId: string,
  userStory: CreateUserStoryRequest,
): Promise<UserStory> {
  return request<UserStory>(`/api/projects/${projectId}/user-stories`, {
    method: 'POST',
    body: JSON.stringify(userStory),
  })
}
