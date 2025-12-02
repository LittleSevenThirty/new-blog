interface ArticleSearch {
    // id，默认生成数据比对
    id: number,

    // 文章编号，真实查询文章的
    articleId: string,

    // 文章分类所属名
    categoryName: string,

    // 文章标题
    articleTitle: string,

    // 文章访问数量
    visitedCount: number,

    // 高亮标题
    hilightedTitle: string,

    // 文章内容
    articleContent: string
}

// 提取接口，热门文章推荐
interface HotArticle {
    // 文章编号
    articleId: string,

    // 文章标题
    articleTitle: string,

    // 访问数目
    visitedCount: number
}

export {ArticleSearch,HotArticle}