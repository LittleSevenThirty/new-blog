import { defineStore } from "pinia";
import { shallowRef } from "vue";
import { ArticleSearch } from "../../../apis/article/type.ts";
import { getSearchTitleList } from "../../../apis/article/index.ts"
import { WebsiteInfo } from "../../../apis/website/type.ts";
import { getWebsiteInfo } from "../../../apis/website/index.ts";
import { returnTime } from "../../../utils/tools.ts";

const useWebsiteStore = defineStore("website", () => {
    //// states
    // 站点信息存储对象
    const webInfo = shallowRef<WebsiteInfo>();

    // 文章搜索数据存储对象
    const articleSearch = shallowRef<Array<ArticleSearch>>([]);

    //// actions
    // const getInfo = async ()=>{
    //     getWebsiteInfo().then(res=>{
    //         // 经过测试res已经在响应拦截器里处理过了,res直接就是数据了
    //         // 更新时间
    //         res.lastUpdatetime=returnTime(res.lastUpdatetime) as string;
    //         webInfo.value=res;
    //     })
    // }

    //// getInfo获取网站信息改写，让其看起来更像线性代码
    const getInfo = async () => {
        console.log('开始获取网站信息');
        try {
            let res = await getWebsiteInfo();
            console.log('获取网站信息成功:', res);
            res.data.lastUpdatetime = returnTime(res.data.lastUpdatetime) as string;
            webInfo.value = res.data;
        } catch (error) {
            console.error('获取网站信息失败:', error);
        }
    }

    // 获取网站标题
    const getArticleTitleList = async () => {
        const res = await getSearchTitleList();
        articleSearch.value = res.data;
    }

    // 关键：返回需要暴露的状态和方法
    return {
        webInfo,
        articleSearch,
        getInfo,
        getArticleTitleList
    }
});

export default useWebsiteStore;