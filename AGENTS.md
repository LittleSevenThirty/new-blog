# AGENTS.md

## Purpose

This file is the local working guide for Codex and other coding agents in this repository. Read this before making changes.

The repository is a full-stack blog project with:

- `blog_backend/littleseventhirty-blog`: Spring Boot backend
- `blog_frontend/littleseventhirty-blog`: public blog frontend
- `blog_frontend/littleseventhirty-blog-admin`: admin frontend
- `blog_documents`: project documents
- `articles`, `errors`, `blog_refrences`: supporting materials, not primary runtime code

## Working Rules

- Prefer targeted changes over broad refactors.
- Check for uncommitted local changes before editing. This repo is often in a dirty state.
- Do not overwrite user changes unless explicitly asked.
- Keep edits aligned with the existing style of the touched subproject.
- When changing API contracts, verify backend controller/service/DTO plus both frontends if applicable.
- Avoid committing secrets or copying sensitive config values into new files.

## Project Map

### Backend

Path: `blog_backend/littleseventhirty-blog`

Stack:

- Java 17
- Spring Boot 3.4.0
- Spring Security
- MyBatis-Plus
- Redis
- RabbitMQ
- MinIO

Main class:

- `blog_backend/littleseventhirty-blog/src/main/java/cn/edu/tjufe/zql/Application.java`

Important backend packages:

- `controller`: HTTP endpoints
- `service` and `service/impl`: business logic
- `domain`: DTO / VO / entity models
- `config`: security, redis, mybatis, minio config
- `utils`: shared utilities
- `src/main/resources/mapper`: MyBatis XML mappings
- `src/main/resources/application.yml`: main local config
- `docs/sql`: SQL files (create.sql and insert/ directory)

Default local backend port:

- `8080`

### Public Frontend

Path: `blog_frontend/littleseventhirty-blog`

Stack:

- Vue 3
- Vite
- TypeScript
- Element Plus
- Pinia

Important directories:

- `src/apis`: API wrappers
- `src/views`: page-level views
- `src/components`: shared UI
- `src/router`: route config
- `src/pinia`: stores
- `src/mock`: optional mock data

Default local frontend port:

- `70`

Proxy behavior:

- `/api` is proxied to `VITE_SERVER`
- default `VITE_SERVER` is `http://localhost:8080`

Mock note:

- `src/main.ts` contains a commented mock import
- enable mocks only when backend is unavailable and the task explicitly needs it

### Admin Frontend

Path: `blog_frontend/littleseventhirty-blog-admin`

Stack:

- Vue 3
- Vite
- TypeScript
- Ant Design Vue
- Pinia

Important directories:

- `src/api`: admin API wrappers
- `src/pages`: admin pages
- `src/router`: route generation and guards
- `src/stores`: admin stores
- `src/layouts`: shell layout

Default local admin port:

- `6678`

Proxy behavior:

- `VITE_APP_BASE_API` proxies to `VITE_APP_BASE_URL`
- development default backend is `http://localhost:8080`

## Common Commands

Run commands from each subproject root.

Backend:

- `mvn spring-boot:run`
- `mvn test`

Public frontend:

- `npm install`
- `npm run dev`
- `npm run build`
- `npm run type-check`

Admin frontend:

- `npm install` or `pnpm install`
- `npm run dev`
- `npm run build`
- `npm run typecheck`

Note:

- The admin project declares `pnpm` in `package.json`, but lockfiles and local usage should be checked before changing package-manager-specific files.

## Change Guidelines

### Backend changes

- If adding or changing a field, inspect entity, DTO, VO, service interface, service impl, controller, mapper XML, and frontend request types.
- If changing security-related endpoints, review `SecurityConfiguration` and auth-related utilities.
- If changing file upload behavior, inspect MinIO-related config and `FileUploadUtils`.
- Keep request/response wrappers consistent with existing controller conventions.

### Frontend changes

- Follow the existing component and route structure rather than introducing a new pattern.
- Prefer editing API wrappers and type files together.
- For public frontend UI work, check whether the same content also appears in admin-maintained data structures.
- For admin work, verify route guards, page registration, and API modules together.

### Full-stack changes

- Any change under these backend controllers likely affects frontend callers:
  - `ArticleController`
  - `BannerController`
  - `CategoryController`
  - `CommentController`
  - `FavoriteController`
  - `LeaveWordController`
  - `LikeController`
  - `LogController`
  - `LoginLogController`
  - `PermissionController`
  - `PublicController`
  - `RoleController`
  - `RolePermissionController`
  - `TagController`
  - `TreeHoleController`
  - `UserController`
  - `UserRoleController`
  - `WebsiteInfoController`
- Search both frontends for the endpoint or DTO name before editing.

## Local Environment Assumptions

Based on current config, local development expects:

- MySQL on `localhost:3306`, database `blog`
- Redis on `localhost:6379`
- MinIO on `http://localhost:9000`
- RabbitMQ available locally if message-related flows are exercised

Do not assume production-ready secrets management is in place. The current backend `application.yml` contains sensitive local values and should be treated carefully.

## Files To Check First

When starting a task, usually inspect these first:

- `README.md`
- `blog_backend/littleseventhirty-blog/pom.xml`
- `blog_backend/littleseventhirty-blog/src/main/resources/application.yml`
- `blog_backend/littleseventhirty-blog/docs/sql/create.sql`
- `blog_backend/littleseventhirty-blog/docs/sql/insert/` (SQL insert files)
- `blog_frontend/littleseventhirty-blog/package.json`
- `blog_frontend/littleseventhirty-blog/vite.config.ts`
- `blog_frontend/littleseventhirty-blog-admin/package.json`
- `blog_frontend/littleseventhirty-blog-admin/vite.config.ts`

## Known Repository Realities

- Root README content is limited and partially encoded incorrectly, so source code is a more reliable reference than docs.
- Backend and admin currently have uncommitted local edits; do not revert them unless the user explicitly asks.
- Public frontend contains mock API support, but it is disabled by default.
- This is not a monorepo with one unified package manager workflow; treat backend, public frontend, and admin frontend as separate apps.

## Default Agent Workflow For This Repo

1. Check `git status --short`.
2. Identify which app is affected: backend, public frontend, admin, or more than one.
3. Read the nearest config, route, API, and entry files before editing.
4. Make minimal changes.
5. Run the narrowest useful verification command for the touched app.
6. Report what changed, what was verified, and what remains unverified.
