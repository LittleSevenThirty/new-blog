import { createApp } from "vue";
import './style.css'
import App from './App.vue'
import router from './router'

// 获取应用实例
const app=createApp(App);
// 注册全局指令1-
// app.directive("",)

// 安装路由
app.use(router);

createApp(App).mount('#app')