import Mock from "mockjs";

Mock.mock("/api/tag/list", "get", {
  code: 200,
  message: "success",
  "data|15-25": [
    {
      // 生成一个随机的整数 ID
      "tagId|+1": 1,
      // 生成一个随机的标签名称（中文词）
      tagName: "@CTITLE(2, 4)",
      // 生成文章数量，范围在 0 到 100 之间
      "articleCount|0-100": 1,
      // 生成创建时间，随机在过去的一年内
      createTime: "@DATETIME",
      // 生成更新时间，随机在过去的一周内
      updateTime: "@DATETIME"
    }
  ]
});