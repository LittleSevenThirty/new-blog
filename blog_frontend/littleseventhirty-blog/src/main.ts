import { createApp } from 'vue'
import './style.css'
import App from './App.vue' // ts搞怪
import router from '@/router'   // ts搞怪

// 获取应用实例
const app=createApp(App)
// 安装路由
app.use(router);

createApp(App).mount('#app')