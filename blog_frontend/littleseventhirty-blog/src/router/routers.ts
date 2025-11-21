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
        component: ()=>import("../views/Layout/index.vue"),
        children: []
    }
]