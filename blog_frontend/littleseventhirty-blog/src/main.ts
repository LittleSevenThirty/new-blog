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
// 这里不要使用动态加载，动态加载的mock是在所有页面后启动，所以axios会出现请求错误问题
import './mock/index.ts'; // 如果不想启动后端就想要数据需要将这段注释取消掉，配置mock是为了后端数据未准备好时提供假数据用的
// 引入自定义指令
import vViewRequest from './directives/vViewRequest.ts'


// 获取应用实例
const app = createApp(App);
// 注册全局指令，使用时添加前缀v-
app.directive("view-request",vViewRequest);

// 安装路由
app.use(router);
// 安装pinia
app.use(pinia);
// 安装element-plus
app.use(ElementPlus);

// 错误代码 createApp(App).mount('#app'); 相当于又创建了一个新的app，这个app没有安装router和pinia等插件
app.mount('#app');