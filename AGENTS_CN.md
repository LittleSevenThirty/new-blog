# AGENTS_CN.md

## 用途

这份文件是当前仓库给 Codex 或其他代码代理使用的本地协作说明。开始修改前，优先阅读此文件。

本仓库是一个前后端分离的博客项目，主要包含：

- `blog_backend/littleseventhirty-blog`：Spring Boot 后端
- `blog_frontend/littleseventhirty-blog`：博客前台
- `blog_frontend/littleseventhirty-blog-admin`：博客管理端
- `blog_documents`：项目文档
- `articles`、`errors`、`blog_refrences`：辅助资料目录，不是主运行代码

## 工作规则

- 优先做局部、精确的改动，避免无关的大范围重构。
- 修改前先看工作区是否已有未提交变更，这个仓库经常不是干净状态。
- 不要覆盖用户已有修改，除非用户明确要求。
- 改动时遵循对应子项目现有代码风格，不强行引入新模式。
- 只要接口契约发生变化，就同时核对后端 controller、service、DTO，以及相关前端调用。
- 不要把现有敏感配置复制到新文件里，也不要继续扩散密钥类信息。

## 项目结构

### 后端

路径：`blog_backend/littleseventhirty-blog`

技术栈：

- Java 17
- Spring Boot 3.4.0
- Spring Security
- MyBatis-Plus
- Redis
- RabbitMQ
- MinIO

启动类：

- `blog_backend/littleseventhirty-blog/src/main/java/cn/edu/tjufe/zql/Application.java`

重点目录：

- `controller`：接口入口
- `service`、`service/impl`：业务逻辑
- `domain`：DTO、VO、实体模型
- `config`：安全、Redis、MyBatis、MinIO 等配置
- `utils`：公共工具类
- `src/main/resources/mapper`：MyBatis XML 映射
- `src/main/resources/application.yml`：本地主要配置
- `docs/sql`：SQL 文件（create.sql 和 insert/ 目录）

默认本地端口：

- `8080`

### 博客前台

路径：`blog_frontend/littleseventhirty-blog`

技术栈：

- Vue 3
- Vite
- TypeScript
- Element Plus
- Pinia

重点目录：

- `src/apis`：前台接口封装
- `src/views`：页面级视图
- `src/components`：公共组件
- `src/router`：路由配置
- `src/pinia`：状态管理
- `src/mock`：可选 mock 数据

默认本地端口：

- `70`

代理规则：

- `/api` 会被代理到 `VITE_SERVER`
- 当前默认 `VITE_SERVER` 是 `http://localhost:8080`

Mock 说明：

- `src/main.ts` 里保留了注释掉的 mock 引入
- 只有在后端暂不可用且任务明确需要时，再启用 mock

### 管理端

路径：`blog_frontend/littleseventhirty-blog-admin`

技术栈：

- Vue 3
- Vite
- TypeScript
- Ant Design Vue
- Pinia

重点目录：

- `src/api`：管理端接口封装
- `src/pages`：后台页面
- `src/router`：路由生成与守卫
- `src/stores`：后台状态管理
- `src/layouts`：后台整体布局

默认本地端口：

- `6678`

代理规则：

- `VITE_APP_BASE_API` 会代理到 `VITE_APP_BASE_URL`
- 开发环境默认后端地址是 `http://localhost:8080`

## 常用命令

以下命令都应在对应子项目根目录执行。

后端：

- `mvn spring-boot:run`
- `mvn test`

博客前台：

- `npm install`
- `npm run dev`
- `npm run build`
- `npm run type-check`

管理端：

- `npm install` 或 `pnpm install`
- `npm run dev`
- `npm run build`
- `npm run typecheck`

补充说明：

- 管理端 `package.json` 声明了 `pnpm`，但仓库内实际锁文件和本地使用方式要先确认，再决定是否改包管理器相关文件。

## 修改约定

### 后端改动

- 新增或修改字段时，至少检查 entity、DTO、VO、service 接口、service 实现、controller、mapper XML，以及前端请求类型。
- 涉及认证、鉴权、登录态的修改，要同时检查 `SecurityConfiguration` 和相关安全工具类。
- 涉及文件上传时，要一起看 MinIO 配置和 `FileUploadUtils`。
- controller 的返回结构尽量保持和现有封装方式一致，不要随意改返回格式。

### 前端改动

- 优先沿用现有组件组织、路由组织和接口封装方式，不额外创造新分层。
- 改接口时，接口封装与类型定义尽量同步更新。
- 做前台页面改动时，要顺手确认对应数据是不是由管理端维护。
- 做管理端页面改动时，要一起检查路由注册、页面入口和 API 模块。

### 全栈联动改动

- 后端下列 controller 的变更很可能影响前端调用：
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
- 修改前先在两个前端里搜索对应接口路径、DTO 名称或方法名。

## 本地环境假设

按当前配置，本地开发默认依赖：

- MySQL：`localhost:3306`，数据库名 `blog`
- Redis：`localhost:6379`
- MinIO：`http://localhost:9000`
- RabbitMQ：涉及消息流时默认本地可用

注意：

- 当前后端 `application.yml` 已经包含敏感本地配置，应谨慎处理。
- 不要把这些配置原样搬运到文档、日志或新文件中。

## 开始任务时优先查看的文件

- `README.md`
- `blog_backend/littleseventhirty-blog/pom.xml`
- `blog_backend/littleseventhirty-blog/src/main/resources/application.yml`
- `blog_backend/littleseventhirty-blog/docs/sql/create.sql`
- `blog_backend/littleseventhirty-blog/docs/sql/insert/` (SQL 插入文件)
- `blog_frontend/littleseventhirty-blog/package.json`
- `blog_frontend/littleseventhirty-blog/vite.config.ts`
- `blog_frontend/littleseventhirty-blog-admin/package.json`
- `blog_frontend/littleseventhirty-blog-admin/vite.config.ts`

## 当前仓库的现实情况

- 根目录 `README.md` 信息有限，而且部分内容有编码问题，可信度不如源码本身。
- 后端和管理端当前存在未提交改动，除非用户明确要求，否则不要回退。
- 博客前台内置了 mock 方案，但默认没有启用。
- 这个仓库不是统一包管理器的一体化 monorepo，应把后端、前台、管理端视为三个相对独立的应用。

## 默认执行流程

1. 先看 `git status --short`。
2. 判断本次任务影响后端、前台、管理端中的哪一个或哪几个。
3. 修改前先读最近的配置文件、入口文件、路由文件和接口封装。
4. 只做完成任务所需的最小改动。
5. 对改动所在子项目执行最小必要的验证命令。
6. 最后说明改了什么、验证了什么、还有什么未验证。
