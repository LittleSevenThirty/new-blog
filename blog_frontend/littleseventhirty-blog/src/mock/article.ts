// 用户相关接口 mock规则
import Mock from "mockjs"

// 该接口测试可以使用
// 调整后符合后端格式的Mock假数据
Mock.mock("/api/article/hot", "get", {
  code: "200", // 后端是字符串类型，这里改成"200"
  msg: "success（成功）", // 同步后端的msg内容
  data: [
    {
      "articleId|+1": 5, // 从5开始自增，匹配后端示例
      "articleTitle": "test@cname", // 自动生成带test前缀的中文标题
      "visitedCount|1-100": 32 // 随机生成1-100的访问量
    },
    {
      "articleId|+1": 8,
      "articleTitle": "test@cname",
      "visitedCount|1-100": 25
    },
    {
      "articleId|+1": 1,
      "articleTitle": "test@cname",
      "visitedCount|1-100": 20
    },
    {
      "articleId|+1": 2,
      "articleTitle": "test@cname",
      "visitedCount|1-100": 15
    },
    {
      "articleId|+1": 3,
      "articleTitle": "test@cname",
      "visitedCount|1-100": 34
    }
  ]
})

// 获取初始化标题数据
Mock.mock("/api/article/search/init/title", "get", {
  code: 200,
  msg: 'success',
  data: [

  ]
})


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

Mock.mock('/api/article/search/by/content', 'get', (options) => {
  // 从 URL 中提取查询参数
  const queryString = options.url.split('?')[1]
  const params = new URLSearchParams(queryString)
  const content = params.get('content') || ''

  // 模拟返回数据
  const articles = Mock.mock({
    'list|1-5': [{
      id: '@increment',
      title: () => `${content ? content : '搜索'}相关文章@id()`,
      summary: '@cparagraph(1, 2)',
      createdAt: '@datetime'
    }]
  }).list

  return {
    code: 200,
    message: 'success',
    data: articles
  }
})