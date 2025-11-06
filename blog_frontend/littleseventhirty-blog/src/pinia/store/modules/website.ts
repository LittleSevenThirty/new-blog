import {defineStore} from "pinia";
import { shallowRef } from "vue";
import {ArticleSearch} from "../../../apis/article/type.ts";
import {WebsiteInfo} from "../../../apis/website/type.ts";

const useWebsiteStore=defineStore("website",()=>{
    //// states
    // 站点信息存储对象
    const webInfo = shallowRef<WebsiteInfo>();

    // 文章搜索数据存储对象
    const articleSearch = shallowRef<Array<ArticleSearch>>();

    //// actions
    const getInfo = async ()=>{
        // getWebsiteInfo()
    }
});

export default useWebsiteStore;