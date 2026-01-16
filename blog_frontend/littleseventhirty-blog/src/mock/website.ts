// 有这么个联系线，Mock是为了响应前端请求所以对应了api/website/index.ts对应方法，而data对应了api/website/type.ts
import Mock from 'mockjs';

Mock.mock("/api/websiteInfo/front","get",{
  code: 200,
  status: "success",
  data: {
    webMasterAvater: "https://example.com/avatar.jpg",
    webMasterName: "张三",
    webMasterCopy: "热爱开源、前端开发与写作的独立开发者。",
    webMasterProfileBackground: "https://example.com/background.jpg",
    giteeLink: "https://gitee.com/zhangsan",
    githubLink: "https://github.com/zhangsan",
    websiteName: "小小七的博客",
    headerNotification: "🎉 欢迎来到我的博客！新文章已上线～",
    sidebarAnnouncement: "📢 本站已支持暗色主题，欢迎体验！",
    recordInfo: "浙ICP备12345678号-1",
    startTime: "2020-01-01",
    lastUpdatetime: "2026-01-08",
    articleCount: "128",
    categoryCount: "12",
    commentCount: "356",
    wordCount: "245,678",
    visitedCount: "98,765"
  }
})