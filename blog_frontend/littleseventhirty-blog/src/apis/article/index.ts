import { AxiosResponse } from "axios";
import http from "../../utils/http";
import { ArticleSearch } from "./type.ts";

// 获得初始化时标题搜索数据 文章 搜索 初始化 标题
export function getSearchTitleList(): Promise<Array<ArticleSearch>> {
    return http.get("/article/search/init/title", {
        method: "get"
    });
}

// 对内容进行文章搜索 文章 搜索 by 内容
export function searchArticleByContent(content: string) {
    return http.get("/article/search/by/content", {
        params: {
            content
        },
        method: "get"
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