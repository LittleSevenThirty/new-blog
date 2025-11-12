import { createApp } from "vue";
import './style.css'
import App from './App.vue'
import router from './router'
import pinia from "./pinia/store";

// 获取应用实例
const app=createApp(App);
// 注册全局指令1-
// app.directive("",)

// 安装路由
app.use(router);
app.use(pinia);

createApp(App).mount('#app')