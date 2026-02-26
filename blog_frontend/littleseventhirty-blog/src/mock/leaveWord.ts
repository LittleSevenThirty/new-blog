import Mock from "mockjs";

Mock.mock('/api/leaveWord/auth/userLeaveWord', 'post', (options) => {
  const { content } = JSON.parse(options.body);
  // 模拟成功响应
  return {
    code: 200,
    msg: '留言提交成功',
    data: null
  };
});

Mock.mock(RegExp('/api/leaveWord/list' + ".*"), 'get', (options) => {
  let id = Number(options.url.match(/[?&]id=([^&]+)/)?.[1]);
  console.log(id);
  if (!id) id = 1;
  console.log(id);

  // 模拟留言列表数据
  const leaveWordList = [
    {
      leaveWordId: 1,
      userId: 101,
      content: '这是一条测试留言',
      createTime: new Date().toISOString(),
      nickname: '测试用户',
      avatar: 'https://example.com/avatar.jpg',
      commentCount: 5,
      likeCount: 10,
      favoriteCount: 3
    },
    {
      leaveWordId: 2,
      userId: 102,
      content: '这是第二条测试留言',
      createTime: new Date().toISOString(),
      nickname: '用户二',
      avatar: 'https://example.com/avatar2.jpg',
      commentCount: 2,
      likeCount: 8,
      favoriteCount: 1
    }
  ];
  // 按 id 过滤（可选）
  const filteredList = id ? leaveWordList.filter(item => item.leaveWordId == id) : leaveWordList;
  return {
    code: 200,
    msg: '获取成功',
    data: filteredList
  };
});