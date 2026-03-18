interface WebsiteInfo {
    // 站长头像地址
    webmasterAvatar: string,

    // 站长名称
    webmasterName: string,

    // 站长个人简介
    webmasterCopy: string,

    // 站长个人主页背景图片
    webmasterProfileBackground: string,

    // 站长的 Gitee 代码仓库链接
    giteeLink: string,

    // 站长的 GitHub 代码仓库链接
    githubLink: string,

    // 站点的名称
    websiteName: string,

    // 站点头部的通知文案
    headerNotification: string,

    // 站点侧边通知
    sidebarAnnouncement: string,

    // 站点的备案信息
    recordInfo: string,

    // 站点开始时间
    startTime: string,

    // 最近更新时间
    lastUpdatetime: string,

    // 文章数目
    articleCount: string,

    // 分类数目
    categoryCount: string,

    // 评论数目
    commentCount: string,

    // 字数总数
    wordCount: string,

    // 访问量
    visitedCount: string
}

interface slideshowItem {
    image: String    // 轮播图接口
    // 其它字段
}

export { WebsiteInfo, slideshowItem };