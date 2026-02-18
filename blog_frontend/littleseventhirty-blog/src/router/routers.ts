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
            },
            {
                path: "/category/:id?",
                component: () => import("../views/Pigeonhole/Category/index.vue"),
                meta: {
                    title: "文章分类"
                }
            }
        ]
    },
    // 只能放在最后的作为重定向的错误页，后期选择加上错误组件
    {
        path: "/:pathMatch(.*)*",
        name: "not-found",
        redirect: "/"
    }
]