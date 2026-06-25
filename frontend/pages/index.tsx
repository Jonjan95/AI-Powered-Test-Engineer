import { FormEvent, useEffect, useMemo, useState } from 'react'
import {
  createProject,
  createUserStory,
  getProjects,
  getUserStories,
} from '../lib/api'
import type { Project, UserStory } from '../types/api'

export default function Home() {
  const [projects, setProjects] = useState<Project[]>([])
  const [selectedProjectId, setSelectedProjectId] = useState<string | null>(null)
  const [userStories, setUserStories] = useState<UserStory[]>([])
  const [isLoadingProjects, setIsLoadingProjects] = useState(true)
  const [isLoadingStories, setIsLoadingStories] = useState(false)
  const [isCreatingProject, setIsCreatingProject] = useState(false)
  const [isCreatingStory, setIsCreatingStory] = useState(false)
  const [projectError, setProjectError] = useState<string | null>(null)
  const [storyError, setStoryError] = useState<string | null>(null)
  const [projectName, setProjectName] = useState('')
  const [projectDescription, setProjectDescription] = useState('')
  const [storyTitle, setStoryTitle] = useState('')
  const [storyDescription, setStoryDescription] = useState('')
  const [storyAcceptanceCriteria, setStoryAcceptanceCriteria] = useState('')

  const selectedProject = useMemo(
    () => projects.find((project) => project.id === selectedProjectId) ?? null,
    [projects, selectedProjectId],
  )

  useEffect(() => {
    async function loadProjects() {
      try {
        setIsLoadingProjects(true)
        setProjectError(null)
        const nextProjects = await getProjects()
        setProjects(nextProjects)
        setSelectedProjectId((currentProjectId) => currentProjectId ?? nextProjects[0]?.id ?? null)
      } catch (error) {
        setProjectError(error instanceof Error ? error.message : 'Could not load projects.')
      } finally {
        setIsLoadingProjects(false)
      }
    }

    loadProjects()
  }, [])

  useEffect(() => {
    async function loadUserStories(projectId: string) {
      try {
        setIsLoadingStories(true)
        setStoryError(null)
        const nextUserStories = await getUserStories(projectId)
        setUserStories(nextUserStories)
      } catch (error) {
        setStoryError(error instanceof Error ? error.message : 'Could not load user stories.')
      } finally {
        setIsLoadingStories(false)
      }
    }

    if (selectedProjectId) {
      loadUserStories(selectedProjectId)
    } else {
      setUserStories([])
    }
  }, [selectedProjectId])

  async function handleCreateProject(event: FormEvent<HTMLFormElement>) {
    event.preventDefault()

    const name = projectName.trim()
    if (!name) return

    try {
      setIsCreatingProject(true)
      setProjectError(null)
      const createdProject = await createProject({
        name,
        description: projectDescription.trim() || undefined,
      })
      setProjects((currentProjects) => [createdProject, ...currentProjects])
      setSelectedProjectId(createdProject.id)
      setProjectName('')
      setProjectDescription('')
    } catch (error) {
      setProjectError(error instanceof Error ? error.message : 'Could not create project.')
    } finally {
      setIsCreatingProject(false)
    }
  }

  async function handleCreateStory(event: FormEvent<HTMLFormElement>) {
    event.preventDefault()

    const title = storyTitle.trim()
    if (!title || !selectedProjectId) return

    try {
      setIsCreatingStory(true)
      setStoryError(null)
      const createdStory = await createUserStory(selectedProjectId, {
        title,
        description: storyDescription.trim() || undefined,
        acceptanceCriteria: storyAcceptanceCriteria.trim() || undefined,
      })
      setUserStories((currentUserStories) => [createdStory, ...currentUserStories])
      setStoryTitle('')
      setStoryDescription('')
      setStoryAcceptanceCriteria('')
    } catch (error) {
      setStoryError(error instanceof Error ? error.message : 'Could not create user story.')
    } finally {
      setIsCreatingStory(false)
    }
  }

  return (
    <main className="min-h-screen bg-gray-50">
      <div className="mx-auto flex max-w-7xl flex-col gap-8 px-6 py-8 lg:px-8">
        <header className="border-b border-gray-200 pb-6">
          <p className="text-sm font-medium text-blue-700">AI-Powered Test Engineer</p>
          <h1 className="mt-2 text-3xl font-semibold text-gray-950">Projects and user stories</h1>
        </header>

        <div className="grid gap-6 lg:grid-cols-[360px_1fr]">
          <section className="space-y-6">
            <form className="rounded-lg border border-gray-200 bg-white p-5 shadow-sm" onSubmit={handleCreateProject}>
              <h2 className="text-lg font-semibold text-gray-950">Create project</h2>
              <div className="mt-4 space-y-4">
                <label className="block">
                  <span className="text-sm font-medium text-gray-700">Name</span>
                  <input
                    className="mt-1 w-full rounded-md border border-gray-300 px-3 py-2 text-sm outline-none focus:border-blue-600 focus:ring-2 focus:ring-blue-100"
                    maxLength={255}
                    onChange={(event) => setProjectName(event.target.value)}
                    placeholder="Customer portal"
                    required
                    value={projectName}
                  />
                </label>
                <label className="block">
                  <span className="text-sm font-medium text-gray-700">Description</span>
                  <textarea
                    className="mt-1 min-h-[84px] w-full rounded-md border border-gray-300 px-3 py-2 text-sm outline-none focus:border-blue-600 focus:ring-2 focus:ring-blue-100"
                    onChange={(event) => setProjectDescription(event.target.value)}
                    placeholder="Tests for the customer-facing portal"
                    value={projectDescription}
                  />
                </label>
                <button
                  className="w-full rounded-md bg-gray-950 px-4 py-2 text-sm font-semibold text-white hover:bg-gray-800 disabled:cursor-not-allowed disabled:bg-gray-400"
                  disabled={isCreatingProject}
                  type="submit"
                >
                  {isCreatingProject ? 'Creating...' : 'Create project'}
                </button>
              </div>
            </form>

            <section className="rounded-lg border border-gray-200 bg-white p-5 shadow-sm">
              <div className="flex items-center justify-between gap-4">
                <h2 className="text-lg font-semibold text-gray-950">Projects</h2>
                {isLoadingProjects ? <span className="text-sm text-gray-500">Loading...</span> : null}
              </div>

              {projectError ? (
                <p className="mt-4 rounded-md border border-red-200 bg-red-50 px-3 py-2 text-sm text-red-700">
                  {projectError}
                </p>
              ) : null}

              <div className="mt-4 space-y-2">
                {!isLoadingProjects && projects.length === 0 ? (
                  <p className="text-sm text-gray-500">No projects yet.</p>
                ) : null}

                {projects.map((project) => (
                  <button
                    className={`w-full rounded-md border px-3 py-3 text-left transition ${
                      project.id === selectedProjectId
                        ? 'border-blue-600 bg-blue-50'
                        : 'border-gray-200 bg-white hover:border-gray-300 hover:bg-gray-50'
                    }`}
                    key={project.id}
                    onClick={() => setSelectedProjectId(project.id)}
                    type="button"
                  >
                    <span className="block text-sm font-semibold text-gray-950">{project.name}</span>
                    <span className="mt-1 block text-sm text-gray-500">
                      {project.description || 'No description'}
                    </span>
                  </button>
                ))}
              </div>
            </section>
          </section>

          <section className="space-y-6">
            {selectedProject ? (
              <div className="space-y-6">
                <div className="border-b border-gray-200 pb-5">
                  <p className="text-sm font-medium text-gray-500">Selected project</p>
                  <h2 className="mt-1 text-2xl font-semibold text-gray-950">{selectedProject.name}</h2>
                  <p className="mt-2 text-sm leading-6 text-gray-600">
                    {selectedProject.description || 'No description has been added for this project.'}
                  </p>
                </div>

                <form className="rounded-lg border border-gray-200 bg-gray-50 p-5" onSubmit={handleCreateStory}>
                  <h3 className="text-lg font-semibold text-gray-950">Create user story</h3>
                  <div className="mt-4 grid gap-4 md:grid-cols-2">
                    <label className="block md:col-span-2">
                      <span className="text-sm font-medium text-gray-700">Title</span>
                      <input
                        className="mt-1 w-full rounded-md border border-gray-300 px-3 py-2 text-sm outline-none focus:border-blue-600 focus:ring-2 focus:ring-blue-100"
                        maxLength={255}
                        onChange={(event) => setStoryTitle(event.target.value)}
                        placeholder="Customer signs in"
                        required
                        value={storyTitle}
                      />
                    </label>
                    <label className="block">
                      <span className="text-sm font-medium text-gray-700">Description</span>
                      <textarea
                        className="mt-1 min-h-[108px] w-full rounded-md border border-gray-300 px-3 py-2 text-sm outline-none focus:border-blue-600 focus:ring-2 focus:ring-blue-100"
                        onChange={(event) => setStoryDescription(event.target.value)}
                        placeholder="As a customer, I want to sign in to my account."
                        value={storyDescription}
                      />
                    </label>
                    <label className="block">
                      <span className="text-sm font-medium text-gray-700">Acceptance criteria</span>
                      <textarea
                        className="mt-1 min-h-[108px] w-full rounded-md border border-gray-300 px-3 py-2 text-sm outline-none focus:border-blue-600 focus:ring-2 focus:ring-blue-100"
                        onChange={(event) => setStoryAcceptanceCriteria(event.target.value)}
                        placeholder="Valid credentials open the account dashboard."
                        value={storyAcceptanceCriteria}
                      />
                    </label>
                  </div>
                  <button
                    className="mt-4 rounded-md bg-blue-700 px-4 py-2 text-sm font-semibold text-white hover:bg-blue-600 disabled:cursor-not-allowed disabled:bg-blue-300"
                    disabled={isCreatingStory}
                    type="submit"
                  >
                    {isCreatingStory ? 'Creating...' : 'Create user story'}
                  </button>
                </form>

                <section>
                  <div className="flex items-center justify-between gap-4">
                    <h3 className="text-lg font-semibold text-gray-950">User stories</h3>
                    {isLoadingStories ? <span className="text-sm text-gray-500">Loading...</span> : null}
                  </div>

                  {storyError ? (
                    <p className="mt-4 rounded-md border border-red-200 bg-red-50 px-3 py-2 text-sm text-red-700">
                      {storyError}
                    </p>
                  ) : null}

                  <div className="mt-4 space-y-3">
                    {!isLoadingStories && userStories.length === 0 ? (
                      <p className="rounded-md border border-dashed border-gray-300 px-4 py-8 text-center text-sm text-gray-500">
                        No user stories yet.
                      </p>
                    ) : null}

                    {userStories.map((userStory) => (
                      <article className="rounded-lg border border-gray-200 p-4" key={userStory.id}>
                        <h4 className="font-semibold text-gray-950">{userStory.title}</h4>
                        <p className="mt-2 text-sm leading-6 text-gray-600">
                          {userStory.description || 'No description'}
                        </p>
                        <div className="mt-4 rounded-md bg-gray-50 p-3">
                          <p className="text-xs font-semibold uppercase tracking-wide text-gray-500">
                            Acceptance criteria
                          </p>
                          <p className="mt-1 text-sm leading-6 text-gray-700">
                            {userStory.acceptanceCriteria || 'No acceptance criteria'}
                          </p>
                        </div>
                      </article>
                    ))}
                  </div>
                </section>
              </div>
            ) : (
              <div className="flex min-h-[420px] items-center justify-center rounded-lg border border-dashed border-gray-300 px-6 text-center">
                <div>
                  <h2 className="text-xl font-semibold text-gray-950">Select a project</h2>
                  <p className="mt-2 text-sm text-gray-500">
                    Create or choose a project to view and add user stories.
                  </p>
                </div>
              </div>
            )}
          </section>
        </div>
      </div>
    </main>
  )
}
