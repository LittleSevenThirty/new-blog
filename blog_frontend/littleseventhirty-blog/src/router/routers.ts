import component from "element-plus/es/components/tree-select/src/tree-select-option.mjs";

export const constantRouter = [
    // {
    //     path:,
    //     name:,
    //     component:,
    //     meta,
    //     chldren
    // },
    {
        path: "/",
        name: "layout",
        component: () => import("../views/Layout/index.vue"),
        children: [
            {
                // 默认匹配，即默认嵌套router-view展示页面
                path: "",
                name: "home",
                component: () => import("../views/Home/index.vue"),
                // 配置源数据 可通过$route.meta获取
                meta: {
                    title: "littleseven-blog | 不断学习的博客"
                }
            },
            {
                path: "/timeline",
                component: () => import("../views/Pigeonhole/Timeline/index.vue"),
                name: "timeline",
                meta: {
                    title: "时间轴"
                }
            }
        ]
    }
]