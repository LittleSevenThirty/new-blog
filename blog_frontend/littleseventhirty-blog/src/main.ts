import { createApp } from "vue";
import './style.css';
import App from './App.vue';
import router from './router';
import pinia from "./pinia/store";
import ElementPlus from 'element-plus';

// 获取应用实例
const app=createApp(App);
// 注册全局指令1-
// app.directive("",)

// 安装路由
app.use(router);
// 安装pinia
app.use(pinia);
// 安装element-plus
app.use(ElementPlus);

// 错误代码 createApp(App).mount('#app'); 相当于又创建了一个新的app，这个app没有安装router和pinia等插件
app.mount('#app');