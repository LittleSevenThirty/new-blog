// 封装axios
import axios, { AxiosInstance ,AxiosResponse,InternalAxiosRequestConfig} from "axios";

// axios实例
const http: AxiosInstance = axios.create({
    baseURL: import.meta.env.VITE_APP_BASE_API ?? "/",
    timeout: 8000,
    headers: {
        "Content-Type": "application/json;charset=UTF-8"
    }
});

// 请求拦截器
http.interceptors.request.use(
    (config: InternalAxiosRequestConfig)=>{
        console.log("全局请求拦截器");
        return config;
    },
    (error: any)=>{
        return Promise.reject(error);
    }
);

// 响应拦截器
http.interceptors.response.use(
    (response: AxiosResponse)=>{
        // console.log("响应请求拦截器");
        // console.log(response);

        // return response;
        return response.data;
    },
    (error: any)=>{
        return Promise.reject(error);
    }
)

export default http;