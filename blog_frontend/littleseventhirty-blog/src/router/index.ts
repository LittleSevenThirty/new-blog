// 配置vue-router
import {createRouter,createWebHistory} from 'vue-router';
import {constantRouter} from "@/router/routers.ts";

const router = createRouter({
    history: createWebHistory(),
    routes : constantRouter
})

export default router;