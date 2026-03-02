// 封装axios
import axios, { AxiosError, AxiosInstance, AxiosResponse, InternalAxiosRequestConfig } from "axios";

// axios实例
const http: AxiosInstance = axios.create({
    // @ts-ignore
    baseURL: import.meta.env.VITE_APP_BASE_API ?? "/",
    timeout: 8000,
    headers: {
        "Content-Type": "application/json;charset=UTF-8"
    }
});

// 请求拦截器
http.interceptors.request.use(
    (config: InternalAxiosRequestConfig) => {
        console.log("全局请求拦截器");
        const tokenStr = localStorage.getItem('token') || sessionStorage.getItem('token');
        if (tokenStr) {
            try {
                const authObject = JSON.parse(tokenStr);
                if (authObject.token) {
                    config.headers.Authorization = `Bearer ${authObject.token}`;
                }
            } catch (e) {
                console.error('Token解析错误:', e);
            }
        }
        return config;
    },
    (error: AxiosError) => {
        return Promise.reject(error);
    }
);

// 响应拦截器
http.interceptors.response.use(
    (response: AxiosResponse) => {
        // console.log("响应请求拦截器");
        // console.log(response);

        return response.data;
        // return response.data;
    },
    (error: AxiosError) => {
        return Promise.reject(error);
    }
)

export default http;