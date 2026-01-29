import { defineStore } from "pinia";
import {ref} from 'vue';

// 管理文章分页的仓库
const usePaginationStore=defineStore("pagination",()=>{
  const articlePagination=ref({
    // 当前页
    current: 1,
    // 每页条数
    pageSize:10,
    // 总条数
    total:0
  });

  return {
    articlePagination
  }
});

export default usePaginationStore;