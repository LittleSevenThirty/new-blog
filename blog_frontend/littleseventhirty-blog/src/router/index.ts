// 配置vue-router
import {createRouter,createWebHistory} from 'vue-router';
import {constantRouter} from "@/router/routers.ts"; // 不用管，能正常运行，ts搞怪呢

const router = createRouter({
    history: createWebHistory(),
    routes : constantRouter
})

export default router;