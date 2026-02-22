import Mock from "mockjs"

Mock.mock("/api/treeHole/auth/addTreeHole", "post", {
  code: 200,
  msg: "success",
  data: null
})

Mock.mock("/api/treeHole/treeHoleList", "get", {
  "code": 200,
  "msg": "查询成功",
  // 使用 |10-20 语法，随机生成 10 到 20 条数据
  "data|10-20": [
    {
      "nickName": "@cname", // 随机生成中文姓名
      // 生成随机图片占位符
      "avater": "@image('100x100', '#4A90E2', '#FFF', 'User')",
      "content": "@cparagraph(1, 3)", // 随机生成 1 到 3 句中文段落
    }
  ]
});