// 配置vue-router
import {createRouter,createWebHistory} from 'vue-router';
// @ts-ignore
import {constantRouter} from "@/router/routers.ts";

const router = createRouter({
    history: createWebHistory(),
    routes : constantRouter
})

export default router;