// 用户相关接口 mock规则
import Mock from "mockjs"

// 该接口测试可以使用
Mock.mock("/api/article/hot","get",{
  code: 200,
  msg: 'success',
  data: {
    'list|5': [{
      'articleId|+1': 1,
      'articleTitle': '@cname',
      'visitedCount|+1': 123 
    }]
  }
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

Mock.mock("api/article/search/init/title","get",{
  code: 200,
  msg: 'success',
  data: {
    'list|10': [{
      'articleId|+1': 1,
      'articleTitle': '@cname',
      'categoryName': '@name',
      'visitedCount|+1': 123 
    }]
  }
})
