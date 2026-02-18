import Mock from 'mockjs'

// 1. 定义接口 URL
// 使用正则匹配，以防止 url 中后续带参数导致匹配失败
// 记得加上拦截器添加的 /api 前缀
const categoryListUrl = /\/api\/category\/list/

// 2. 定义 Mock 规则
Mock.mock(categoryListUrl, 'get', {
  "code": 200, // 对应统一返回格式的 code
  "msg": "操作成功", // 对应 msg
  // data 对应 List<CategoryVO>，生成 5 到 15 条随机数据
  "data|5-15": [
    {
      // categoryId: Long -> 使用自增数字或随机 ID
      "categoryId|+1": 100,

      // categoryName: String -> 使用随机中文标题 (3-6字)
      "categoryName": "@ctitle(3, 6)",

      // articleCount: Long -> 随机整数
      "articleCount": "@integer(0, 500)",

      // createTime: Date -> 格式化日期字符串
      "createTime": "@datetime('yyyy-MM-dd HH:mm:ss')",

      // updateTime: Date -> 格式化日期字符串
      "updateTime": "@datetime('yyyy-MM-dd HH:mm:ss')"
    }
  ]
})