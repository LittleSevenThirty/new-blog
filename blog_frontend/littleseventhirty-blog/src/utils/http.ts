// 封装axios
import axios, { AxiosError, AxiosInstance, AxiosResponse, InternalAxiosRequestConfig } from "axios";
import { REQUEST_LOADING_PATH } from "../utils/enums.ts"
import useLoadingStore from "../pinia/store/modules/loading.ts"
import NProgress from 'nprogress' // 导入NProgress轻量级进度条插件
import { GET_TOKEN } from "./auth.ts";
import { Jwt_Prefix } from "../const/jwt.ts";
import { ElMessage, ElNotification } from "element-plus";
// axios实例
const http: AxiosInstance = axios.create({
    // @ts-ignore
    baseURL: import.meta.env.VITE_APP_BASE_API ?? "/",
    timeout: 8000,
    headers: {
        "Content-Type": "application/json;charset=UTF-8"
    }
});

// @ts-ignore;
const env = import.meta.env;
const firstRequestPaths = new Set();
const pathRequestCount = new Map();
let loadingShown = false;

// 请求拦截器
http.interceptors.request.use(
    (config: InternalAxiosRequestConfig) => {
        const url = config.url; // 参考：/websiteInfo/front
        // console.log("全局请求拦截器启动，本次请求URL：" + url);
        // console.log("请求配置：", config);
        // @ts-ignore
        const musicBaseApi = import.meta.env.VITE_MUSIC_BASE_API;
        if (musicBaseApi && url?.startsWith(musicBaseApi)) {
            config.baseURL = "";
        }
        let matchingPath = REQUEST_LOADING_PATH.find(path => url?.startsWith(path));

        if (!(url?.startsWith(env.VITE_YIYAN_API)) || matchingPath) {
            if (matchingPath && !firstRequestPaths.has(matchingPath)) { // 仅在第一次请求时
                // 记录非首次请求url
                firstRequestPaths.add(matchingPath);
                pathRequestCount.set(matchingPath, (pathRequestCount.get(matchingPath) || 0) + 1);
                if (!loadingShown) {
                    loadingShown = true;
                    const loadingStore = useLoadingStore();
                    loadingStore.show();
                    NProgress.start();
                }
            } else NProgress.start();
        }


        config.headers['X-Client-Type'] = 'Frontend'
        // 请求头添加token
        if (GET_TOKEN() == null) return config
        config.headers['Authorization'] = Jwt_Prefix + GET_TOKEN()  // 自定义请求头

        return config
    },
    (error: AxiosError) => {
        // console.error("请求错误：", error);
        return Promise.reject(error);
    }
);

// 响应拦截器
http.interceptors.response.use(
    (response) => {
        // console.log("全局响应拦截器启动，本次请求URL：" + response.config.url);
        // console.log("响应内容：", response);
        let url = response.config?.url;
        let matchingPath = REQUEST_LOADING_PATH.find(path => url?.startsWith(path));

        if (matchingPath) {
            pathRequestCount.set(matchingPath, pathRequestCount.get(matchingPath) - 1);

            if (pathRequestCount.get(matchingPath) === 0) { // 所有特定路径的请求都已完成
                loadingShown = false;
                const loadingStore = useLoadingStore();
                loadingStore.hidden();
                pathRequestCount.clear(); // 清空整个 Map
                NProgress.done();
            }
        } else NProgress.done();

        if (response.data.code === 1012) {
            ElNotification({
                title: '账号已被封禁',
                message: response.data.msg,
                type: 'warning',
            })
        }

        return response.data
    },
    (error: AxiosError) => {
        // console.log("全局响应拦截器启动，本次错误请求URL：" + error.config?.url);
        // console.log("错误响应内容：", error);
        let message = error.message;
        if (message == "Network Error") {
            message = "后端接口连接异常";
        } else if (message.includes("timeout")) {
            message = "系统接口请求超时";
        } else if (message.includes("Request failed with status code")) {
            message = "系统接口" + message.substring(message.length - 3) + "异常";
        }
        // 最后可能是一言接口请求错误
        if (!error?.config?.url?.startsWith("https://v1.hitokoto.cn")) {
            ElMessage.error(message)
        }
        return Promise.reject(error.response)
    }
)

export default http;