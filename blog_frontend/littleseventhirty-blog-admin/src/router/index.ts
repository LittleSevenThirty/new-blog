import { createRouter, createWebHistory } from 'vue-router'
import staticRoutes from './static-routes'
import { rootRoute } from './dynamic-routes'

const router = createRouter({
  routes: [
    rootRoute,
    ...staticRoutes,
  ],
  history: createWebHistory(import.meta.env.VITE_APP_BASE),
})

export default router
