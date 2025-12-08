import { createApp } from "vue";
// 引入全局样式
import './styles/index.scss';
import './styles/tailwindcss.css'
// 引入根组件
import App from './App.vue';
// 引入创建好的路由插件
import router from './router';
// 引入创建好的pinia插件
import pinia from "./pinia/store";
// 引入ElementPlus插件
import ElementPlus from 'element-plus';
// 引入ElementPlus样式
import 'element-plus/dist/index.css';

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