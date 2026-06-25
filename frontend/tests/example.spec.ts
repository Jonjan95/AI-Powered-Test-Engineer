import { expect, test } from '@playwright/test'

test('shows the projects and user stories workflow', async ({ page }) => {
  await page.route('**/api/projects', async (route) => {
    if (route.request().method() === 'GET') {
      await route.fulfill({
        contentType: 'application/json',
        json: [
          {
            id: '11111111-1111-1111-1111-111111111111',
            name: 'Customer portal',
            description: 'Tests for the customer-facing portal',
            createdAt: '2026-06-25T10:00:00Z',
            updatedAt: '2026-06-25T10:00:00Z',
          },
        ],
      })
      return
    }

    await route.fallback()
  })

  await page.route('**/api/projects/*/user-stories', async (route) => {
    if (route.request().method() === 'GET') {
      await route.fulfill({
        contentType: 'application/json',
        json: [
          {
            id: '22222222-2222-2222-2222-222222222222',
            projectId: '11111111-1111-1111-1111-111111111111',
            title: 'Customer signs in',
            description: 'As a customer, I want to sign in to my account.',
            acceptanceCriteria: 'Valid credentials open the account dashboard.',
            createdAt: '2026-06-25T10:05:00Z',
          },
        ],
      })
      return
    }

    await route.fallback()
  })

  await page.goto('/')

  await expect(page.getByRole('heading', { name: 'Projects and user stories' })).toBeVisible()
  await expect(page.getByRole('heading', { name: 'Create project' })).toBeVisible()
  await expect(page.getByRole('heading', { name: 'Projects', exact: true })).toBeVisible()
  await expect(page.getByRole('button', { name: /Customer portal/ })).toBeVisible()
  await expect(page.getByText('Selected project')).toBeVisible()
  await expect(page.getByRole('heading', { name: 'Create user story' })).toBeVisible()
  await expect(page.getByRole('heading', { name: 'User stories', exact: true })).toBeVisible()
  await expect(page.getByRole('heading', { name: 'Customer signs in' })).toBeVisible()
})
