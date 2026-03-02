import { AxiosResponse } from "axios";
import http from "../../utils/http";
import { ArticleSearch } from "./type.ts";
import { METHODS } from "http";

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
export function getRecommendArticleList() {
    return http({
        url: "/article/recommend",
        method: "get"
    });
}

/**
 * 获取所有文章列表
 * @param pageNum 
 * @param pageSize 
 * @returns 
 */
export function getArticleList(pageNum: Number, pageSize: Number) {
    return http({
        url: "/article/list",
        method: "get",
        params: {
            pageNum,
            pageSize
        }
    })
}

// 原/article/where/list
export function getArticleListByType(type: number, typeId: string) {
    return http({
        url: `/article/type/list/${typeId}`,
        method: "get",
        params: {
            type
        }
    });
}

/**
 * 根据分类获得相关推荐
 * @param categoryId 
 * @param articleId 
 * @returns 
 */
export function getRelativeArticle(categoryId: String, articleId: String) {
    return http({
        url: `/article/related/${categoryId}/${articleId}`,
        method: 'get'
    })
}

/**
 * 获取文章时间线数据
 */
export function getTimeline() {
    return http({
        url: "/article/timeline",
        method: "get"
    })
}

// 获取对应id文章
export function getArticleDetail(id: string) {
    return http({
        url: `/article/detail`,
        method: "get",
        params: {
            id
        },
    })
}

// 文章访问量+1
export function addArticleVisit(id: String) {
    return http({
        url: `/article/visit`,
        method: "get",
        params: {
            id
        },
    });
}