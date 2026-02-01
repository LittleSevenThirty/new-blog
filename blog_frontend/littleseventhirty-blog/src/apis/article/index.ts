import { AxiosResponse } from "axios";
import http from "../../utils/http";
import { ArticleSearch } from "./type.ts";

// 获得初始化时标题搜索数据 文章 搜索 初始化 标题
export function getSearchTitleList() {
    return http.get("/article/search/init/title", {
        method: "get"
    });
}

// 对内容进行文章搜索 文章 搜索 by 内容
export function searchArticleByContent(content: string) {
    return http.get("/article/search/by/content", {
        params: {
            content
        }
    })
}

/**
 * 获取热点文章内容
 * @returns 获得热点文章
 */
export function getHotArticleRecommend() {
    return http.get("/article/hot", {
        method: "get"
    });
}

/**
 * 
 * @returns 获取随机文章
 */
export function getRandomArticle() {
  return http({
    url: "/article/random",
    method: "get"
  })
}

/**
 * 
 * @returns 推荐的置顶文章
 */
export function getRecommendArticleList(){
    return http({
        url:"/article/recommend",
        method:"get"
    });
}

export function getArticleList(pageNum:Number,pageSize:Number){
    return http({
        url:"/article/list",
        method:"get",
        params:{
            pageNum,
            pageSize
        }
    })
}