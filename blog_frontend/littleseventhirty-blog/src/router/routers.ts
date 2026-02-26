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
            // 首页
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
            // 时间轴
            {
                path: "/timeline",
                component: () => import("../views/Pigeonhole/Timeline/index.vue"),
                name: "timeline",
                meta: {
                    title: "时间轴"
                }
            },
            // 分类
            {
                path: "/category/:id?",
                component: () => import("../views/Pigeonhole/Category/index.vue"),
                name: "category",
                meta: {
                    title: "文章分类"
                }
            },
            // 标签
            {
                path: "/tags/:id?",
                component: () => import("../views/Pigeonhole/Tags/index.vue"),
                name: "tags",
                meta: {
                    title: "文章标签"
                }
            },
            // 树洞
            {
                path: "/tree-hole",
                component: () => import("../views/Other/TreeHole/index.vue"),
                name: "tree-hole",
                meta: {
                    title: "弹幕树洞"
                }
            },
            // 留言板
            {
                path: "/message",
                component: () => import("../views/Other/Message/index.vue"),
                name: "message",
                children: [
                    // 默认
                    {
                        path: "",
                        component: () => import("../views/Other/Message/MessageList/index.vue"),
                        name: "messageList",
                        meta: {
                            title: "留言板"
                        }
                    },
                    {
                        path: "/message/detail/:id?",
                        component: () => import("../views/Other/Message/MessageDetail/index.vue"),
                        name: "messageDetail",
                        meta: {
                            title: "留言详情"
                        }
                    }
                ]
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