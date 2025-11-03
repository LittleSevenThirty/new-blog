import {defineStore} from "pinia";
import { shallowRef } from "vue";
import {ArticleSearch} from "@/apis/article/type.ts";

const useWebsiteStore=defineStore("website",()=>{
    const webInfo = shallowRef<>();

    // 标题搜索数据存储对象
    const titleSearch = shallowRef<Array<ArticleSearch>>();


});

export default useWebsiteStore;