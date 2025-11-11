import {defineStore} from "pinia";
import { shallowRef } from "vue";
import {ArticleSearch} from "../../../apis/article/type.ts";
import {WebsiteInfo} from "../../../apis/website/type.ts";
import { getWebsiteInfo } from "../../../apis/website/index.ts";

const useWebsiteStore=defineStore("website",()=>{
    //// states
    // 站点信息存储对象
    const webInfo = shallowRef<WebsiteInfo>();

    // 文章搜索数据存储对象
    const articleSearch = shallowRef<Array<ArticleSearch>>();

    //// actions
    const getInfo = async ()=>{
        getWebsiteInfo().then(res=>{
            // 经过测试res已经在响应拦截器里处理过了,res直接就是数据了
            // 更新时间
            res.lastUpdatetime=;
            webInfo.value=res;
        })
    }
});

export default useWebsiteStore;