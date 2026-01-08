// 文章相关接口
// mock规则
// 对接前端使用axios访问后端接口时，但后端没准备好
// 前端接口处于apis/article/index
import Mock from "mockjs"

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

Mock.mock(RegExp('/api/article/search/by/content'+'.*'), 'get', (options) => {
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

Mock.mock("/api/article/random","get",()=>{
  // 1. 先复制数组并打乱顺序（避免修改原数组）
  const shuffledArticles = [...articleList].sort(() => Math.random() - 0.5);
  // 2. 截取前5条作为随机结果
  const random5Articles = shuffledArticles.slice(0, 5);

  const formattedResult = random5Articles.map(article => ({
    articleId: Number(article.articleId), // 匹配VO的Long类型
    articleTitle: article.articleTitle,   // String类型
    visitedCount: Number(article.visitedCount), // 匹配VO的Long类型
    createTime: new Date().getDate()
  }));

  return Mock.mock({
    code: 200,
    message: 'success(成功)',
    data: formattedResult
  })
})