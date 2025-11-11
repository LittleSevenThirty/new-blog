// typescript类型增强，针对vite的env配置

// 配置ImportMetaEnv，让ts知道有哪些vite_变量
interface ImportMetaEnv {
    readonly VITE_APP_BASE_API: string,
    readonly VITE_SERVER: string,
    readonly VITE_FRONTEND_URL: string,
    readonly VITE_ENABLE_DEV_TOOLSBLOCKER: string
}

interface ImportMeta {
    readonly env: ImportMetaEnv
}