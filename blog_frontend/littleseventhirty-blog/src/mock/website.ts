// 有这么个联系线，Mock是为了响应前端请求所以对应了api/website/index.ts对应方法，而data对应了api/website/type.ts
import Mock from 'mockjs';

Mock.mock("/api/websiteInfo/front","get",{
  code: 200,
  status: "success",
  data: {
    webMasterAvater: "../../public/avater.jpg",
    webMasterName: "张三",
    webMasterCopy: "热爱开源、前端开发与写作的独立开发者。",
    webMasterProfileBackground: "https://ts1.tc.mm.bing.net/th/id/R-C.22fc123aa05330a4f68c998766f43e94?rik=lCrVWHF%2fQMpiLA&riu=http%3a%2f%2fimg-download.pchome.net%2fdownload%2f1k1%2fee%2f2k%2folyjst-mvf.jpg&ehk=i84XSCytuXQg0nab4oH5qgZX6ckUsCCvIPIbEH8fu6Y%3d&risl=&pid=ImgRaw&r=0",
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
});

Mock.mock("/api/banner/slideshow","get",{
  code:200,
  status:"success",
  data:[
    "https://ts1.tc.mm.bing.net/th/id/R-C.22fc123aa05330a4f68c998766f43e94?rik=lCrVWHF%2fQMpiLA&riu=http%3a%2f%2fimg-download.pchome.net%2fdownload%2f1k1%2fee%2f2k%2folyjst-mvf.jpg&ehk=i84XSCytuXQg0nab4oH5qgZX6ckUsCCvIPIbEH8fu6Y%3d&risl=&pid=ImgRaw&r=0",
    "https://ts1.tc.mm.bing.net/th/id/R-C.dbeb051cb8c30e2a068326381be5e6df?rik=rMVE%2fiTjFvF80g&riu=http%3a%2f%2fwww.sucaijishi.com%2fuploadfile%2f2023%2f0327%2f20230327011849352.jpeg%3fimageMogr2%2fformat%2fjpg%2fblur%2f1x0%2fquality%2f60&ehk=MM4vaiZOTjfyd9IuMC7AkfcwxbSxAlG9PJhjZ7cMzrs%3d&risl=&pid=ImgRaw&r=0",
    "https://tse4.mm.bing.net/th/id/OIP.ISwMTgLKjg_Okojx035MsAHaEK?rs=1&pid=ImgDetMain&o=7&rm=3",
    "https://ts1.tc.mm.bing.net/th/id/R-C.91589d987ca581f36fe9419b0626cd6a?rik=a8VTb1mV2lwinA&riu=http%3a%2f%2fwww.sucaijishi.com%2fuploadfile%2f2023%2f0327%2f20230327011039179.jpeg%3fimageMogr2%2fformat%2fjpg%2fblur%2f1x0%2fquality%2f60&ehk=6htHRsId%2fhGvWuAhD2aDoI0lGZ%2f1COp6E2BdTXvU7lw%3d&risl=&pid=ImgRaw&r=0",
    "https://ts1.tc.mm.bing.net/th/id/R-C.55f1966b615b41a131db9e993009d61d?rik=XCQ%2bJSCaFKXbZw&riu=http%3a%2f%2fimgs.aixifan.com%2flive%2f1483602004055%2f1483602004055.jpg&ehk=f5jovT%2fWC9O6Xgco6BJ%2ftpaARBfqBYNYQBtVAZUHIg8%3d&risl=&pid=ImgRaw&r=0",
    "https://5b0988e595225.cdn.sohucs.com/images/20190819/48edd475ca1e43fbbd97d7bda9e3b333.jpeg"
  ]
})