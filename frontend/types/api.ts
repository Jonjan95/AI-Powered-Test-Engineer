export type Project = {
  id: string
  name: string
  description: string | null
  createdAt: string
  updatedAt: string
}

export type CreateProjectRequest = {
  name: string
  description?: string
}

export type UserStory = {
  id: string
  projectId: string
  title: string
  description: string | null
  acceptanceCriteria: string | null
  createdAt: string
}

export type CreateUserStoryRequest = {
  title: string
  description?: string
  acceptanceCriteria?: string
}
