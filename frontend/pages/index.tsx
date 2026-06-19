import { useEffect, useState } from 'react'

type Health = {
  status?: string
  service?: string
  error?: string
}

export default function Home() {
  const [health, setHealth] = useState<Health>({})

  useEffect(() => {
    async function fetchHealth() {
      try {
        const res = await fetch('http://localhost:8080/api/health')
        if (!res.ok) throw new Error(`${res.status} ${res.statusText}`)
        const data = await res.json()
        setHealth(data)
      } catch (e: any) {
        setHealth({ error: e.message })
      }
    }
    fetchHealth()
  }, [])

  return (
    <div className="min-h-screen flex items-center justify-center">
      <div className="max-w-md w-full bg-white shadow rounded p-6">
        <h1 className="text-2xl font-semibold mb-4">AI-Powered Test Engineer</h1>
        <p className="text-sm text-gray-500 mb-4">Backend health status</p>
        <div className="border rounded p-4">
          {health.error ? (
            <p className="text-red-600">Error: {health.error}</p>
          ) : (
            <div>
              <p>
                <span className="font-medium">Service:</span> {health.service ?? '—'}
              </p>
              <p>
                <span className="font-medium">Status:</span> {health.status ?? 'Loading...'}
              </p>
            </div>
          )}
        </div>
      </div>
    </div>
  )
}
