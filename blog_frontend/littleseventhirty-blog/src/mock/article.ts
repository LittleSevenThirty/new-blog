// 文章相关接口
// mock规则
// 对接前端使用axios访问后端接口时，但后端没准备好
// 前端接口处于apis/article/index
import Mock from "mockjs"
import { data1, data2 } from "./articleMock";

// 模拟文章数据（基于你的 insert.sql 转换，补充了分类名称）
const articleList = [
  {
    articleId: '1',
    articleTitle: 'test我的编程学习笔记',
    categoryName: '编程基础', // 对应 category_id = 5
    visitedCount: 20,
    articleContent: 'test article 我的编程学习笔记'
  },
  {
    articleId: '2',
    articleTitle: 'test前端框架新特性解析',
    categoryName: '前端开发', // 对应 category_id = 3
    visitedCount: 15,
    articleContent: 'test article 前端框架新特性解析'
  },
  {
    articleId: '3',
    articleTitle: 'test数据分析实战案例',
    categoryName: '数据科学', // 对应 category_id = 8
    visitedCount: 8,
    articleContent: 'test article 数据分析实战案例'
  },
  {
    articleId: '4',
    articleTitle: 'testPython自动化脚本编写',
    categoryName: '后端开发', // 对应 category_id = 2
    visitedCount: 0,
    articleContent: 'test article Python自动化脚本编写'
  },
  {
    articleId: '5',
    articleTitle: 'test设计模式在项目中的应用',
    categoryName: '架构设计', // 对应 category_id = 6
    visitedCount: 32,
    articleContent: 'test article 设计模式在项目中的应用'
  },
  {
    articleId: '6',
    articleTitle: 'test机器学习入门指南',
    categoryName: '人工智能', // 对应 category_id = 4
    visitedCount: 12,
    articleContent: 'test article 机器学习入门指南'
  },
  {
    articleId: '7',
    articleTitle: 'test后端接口性能优化',
    categoryName: '性能优化', // 对应 category_id = 7
    visitedCount: 5,
    articleContent: 'test article 后端接口性能优化'
  },
  {
    articleId: '8',
    articleTitle: 'test产品需求文档撰写技巧',
    categoryName: '产品设计', // 对应 category_id = 1
    visitedCount: 25,
    articleContent: 'test article 产品需求文档撰写技巧'
  }
];

// 该接口测试可以使用
// 调整后符合后端格式的Mock假数据
Mock.mock("/api/article/hot", "get", {
  code: "200", // 后端是字符串类型，这里改成"200"
  msg: "success（成功）", // 同步后端的msg内容
  data: [
    {
      "articleId|+1": 5, // 从5开始自增，匹配后端示例
      "articleTitle": "test我的编程学习笔记", // 自动生成带test前缀的中文标题
      "visitedCount|1-100": 32 // 随机生成1-100的访问量
    },
    {
      "articleId|+1": 8,
      "articleTitle": "test前端框架新特性解析",
      "visitedCount|1-100": 25
    },
    {
      "articleId|+1": 1,
      "articleTitle": "test数据分析实战案例",
      "visitedCount|1-100": 20
    },
    {
      "articleId|+1": 2,
      "articleTitle": "testPython自动化脚本编写",
      "visitedCount|1-100": 15
    },
    {
      "articleId|+1": 3,
      "articleTitle": "test设计模式在项目中的应用",
      "visitedCount|1-100": 34
    }
  ]
});

// 获取初始化标题数据
Mock.mock("/api/article/search/init/title", "get", {
  code: 200,
  msg: 'success',
  data: [
    {
      "articleId|+1": 5, // 从5开始自增，匹配后端示例
      "categoryName": "@cname",
      "articleTitle": "test我的编程学习笔记", // 自动生成带test前缀的中文标题
      "visitedCount|1-100": 32 // 随机生成1-100的访问量
    },
    {
      "articleId|+1": 8,
      "categoryName": "@cname",
      "articleTitle": "test前端框架新特性解析",
      "visitedCount|1-100": 25
    },
    {
      "articleId|+1": 1,
      "categoryName": "@cname",
      "articleTitle": "test数据分析实战案例",
      "visitedCount|1-100": 20
    },
    {
      "articleId|+1": 2,
      "categoryName": "@cname",
      "articleTitle": "testPython自动化脚本编写",
      "visitedCount|1-100": 15
    },
    {
      "articleId|+1": 3,
      "categoryName": "@cname",
      "articleTitle": "test设计模式在项目中的应用",
      "visitedCount|1-100": 34
    },
    {
      "articleId|+1": 4,
      "categoryName": "@cname",
      "articleTitle": "test机器学习入门指南",
      "visitedCount|1-100": 34
    },
    {
      "articleId|+1": 4,
      "categoryName": "@cname",
      "articleTitle": "test后端接口性能优化",
      "visitedCount|1-100": 34
    },
    {
      "articleId|+1": 4,
      "categoryName": "@cname",
      "articleTitle": "test产品需求文档撰写技巧",
      "visitedCount|1-100": 34
    },
  ]
});


// Mock.mock("/api/article/search/by/content","get",(options:Mock.MockjsRequestOptions)=>{
//     // 从 options.url 中可以提取查询参数（如果需要）
//   const url = new URL(options.url, 'http://localhost') // Mock.js 的 options.url 是完整 URL 字符串
//   const content = url.searchParams.get('content') || ''

//   // 根据 content 动态生成结果（可选）
//   const articles = Mock.mock({
//     'list|5': [{
//       articleId: '@increment',
//       articleTitle: () => `${content || '搜索结果'}相关文章@id()`,
//       'visitedCount|+1': 10,
//       categoryName: '@name',
//       articleContent: '@cparagraph(1, 2)'
//     }]
//   }).list
//   return {
//     code: 200,
//     msg: 'success',
//     data: articles
//   }
// })

Mock.mock(RegExp('/api/article/search/by/content' + '.*'), 'get', (options) => {
  console.log(options);
  // 从 URL 中提取查询参数（兼容无参数的情况）
  const queryString = options.url.split('?')[1] || '';
  const params = new URLSearchParams(queryString);
  // 获取搜索关键词，去除首尾空格，统一转小写（不区分大小写）
  const searchContent = (params.get('content') || '').trim().toLowerCase();

  // 模糊匹配逻辑：标题或内容包含搜索关键词
  const filteredArticles = articleList.filter(article => {
    const title = article.articleTitle.toLowerCase();
    const content = article.articleContent.toLowerCase();
    return title.includes(searchContent) || content.includes(searchContent);
  });

  // 返回模拟的响应数据
  return Mock.mock({
    code: 200,
    message: 'success',
    data: filteredArticles
  });
})

Mock.mock("/api/article/random", "get", () => {
  // 1. 先复制数组并打乱顺序（避免修改原数组）
  const shuffledArticles = [...articleList].sort(() => Math.random() - 0.5);
  // 2. 截取前5条作为随机结果
  const random5Articles = shuffledArticles.slice(0, 5);

  const formattedResult = random5Articles.map(article => ({
    articleId: Number(article.articleId), // 匹配VO的Long类型
    articleTitle: article.articleTitle,   // String类型
    visitedCount: Number(article.visitedCount), // 匹配VO的Long类型
    createTime: new Date()
  }));

  return Mock.mock({
    code: 200,
    message: 'success(成功)',
    data: formattedResult
  })
})

// 推荐文章列表接口
Mock.mock("/api/article/recommend", "get", {
  code: 200,
  message: 'success',
  data: [
    {
      "articleId": 1001,
      "articleCover": "https://example.com/covers/tech-01.jpg",
      "articleTitle": "Java 高并发编程实战：从理论到落地",
      "articleContent": "本文详细讲解了Java高并发编程的核心原理，包括线程池、锁机制、CAS操作等，并结合实际项目案例演示了如何解决生产环境中的并发问题。",
      "createTime": "2026-01-20 10:30:00"
    },
    {
      "articleId": 1002,
      "articleCover": "https://picsum.photos/seed/tech1/300/200",
      "articleTitle": "Vue3 + Vite 项目性能优化最佳实践",
      "articleContent": "针对Vue3项目开发中的常见性能瓶颈，本文从打包体积、渲染效率、请求优化三个维度，分享了8个可落地的优化技巧。",
      "createTime": "2026-01-22 15:15:00"
    },
    {
      "articleId": 1003,
      "articleCover": "https://picsum.photos/seed/life2/300/200",
      "articleTitle": "MySQL 索引失效的10种常见场景及解决方案",
      "articleContent": "索引是提升MySQL查询效率的关键，但不当的使用会导致索引失效。本文梳理了10种高频索引失效场景，并给出对应的优化方案和SQL示例。",
      "createTime": "2026-01-23 09:45:00"
    },
    {
      "articleId": 1004,
      "articleCover": "https://picsum.photos/seed/ent3/300/200",
      "articleTitle": "Docker 容器化部署实战：从入门到精通",
      "articleContent": "本文从零开始讲解Docker的核心概念，包括镜像、容器、仓库，然后通过一个SpringBoot项目的容器化部署案例，完整演示Docker的使用流程。",
      "createTime": "2026-01-24 14:20:00"
    },
    {
      "articleId": 1005,
      "articleCover": "https://picsum.photos/seed/finance5/300/200",
      "articleTitle": "大语言模型应用开发：基于LangChain快速搭建智能问答系统",
      "articleContent": "LangChain简化了大语言模型的应用开发流程，本文以智能问答系统为例，讲解如何使用LangChain整合LLM、向量数据库和提示词工程。",
      "createTime": "2026-01-25 11:00:00"
    }
  ]
})

Mock.mock("/api/article/timeline", "get", {
  'code': 200,
  'msg': '操作成功',
  'data|10': [{ // 生成 10 条数据
    'articleId|+1': 1, // 文章ID，从1开始自增
    'articleCover': '@IMAGE("200x100", "#50BFF9", "缩略图")', // 模拟图片链接
    'articleContent': '@CSENTENCE(50, 100)', // 模拟文章内容，50-100个汉字
    'createTime': '@DATETIME("yyyy-MM-dd HH:mm:ss")' // 模拟创建时间
  }]
});

// 搜索文章
Mock.mock(/\/api\/article\/list\/\d+\/\d+/, "get", {
  // 这里假设 code 为 200 表示成功
  code: 200,
  msg: '请求成功',
  // data 是一个数组，使用 Mock.js 的数组语法，生成 3-6 篇相关文章
  'data|3-6': [{
    // 文章 ID，生成随机数字
    'articleId|+1': 1000,
    // 文章缩略图，使用 Mock.js 的图片占位符服务
    'articleCover': "@image('200x120', '#4A7BF7', '#FFF', 'png', '缩略图')",
    // 文章标题，生成随机的中文句子
    'articleTitle': "@ctitle(10, 20)",
    // 创建时间，生成随机日期，格式为 YYYY-MM-DD HH:mm:ss
    'createTime': new Date().getDate().toString()
  }] // 提取数组部分
})

// 假全文章推荐列表
let random = 0;
Mock.mock(RegExp('/api/article/list' + '.*'), "get", () => {
  random += 1;
  return random % 2 == 0 ? data1 : data2;
});