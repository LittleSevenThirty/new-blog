import Mock from 'mockjs';

// 生成随机评论数据的函数
const generateComment = (overrides = {}) => {
  return {
    // 使用 Mock.Random.increment() 生成唯一的 ID，防止前端 key 冲突
    commentId: Mock.Random.increment(),
    commentType: Mock.Random.integer(1, 3), // 1文章 2友链 3说说
    typeId: Mock.Random.integer(1, 100),
    parentId: null, // 默认是父评论
    replyId: null,
    commentContent: Mock.Random.csentence(10, 30), // 随机中文句子
    commentUserId: Mock.Random.id(),
    replyUserId: null,
    createTime: Mock.Random.datetime(),
    commentUserNickname: Mock.Random.cname(),
    commentUserAvatar: Mock.Random.image('100x100', '#ffcc00', '#fff', 'png', 'Avatar'),
    replyUserNickname: null,
    likeCount: Mock.Random.integer(0, 100),
    childCommentCount: 0,
    parentCommentCount: 0,
    childComment: [], // 默认没有子评论
    ...overrides, // 允许外部覆盖
  };
};

// 模拟数据库存储
let commentList = [];

// 初始化一些测试数据
// 生成 20 条测试数据
for (let i = 0; i < 20; i++) {
  commentList.push(generateComment());
}

// 通用响应格式
const successResponse = (data = null, msg = '操作成功') => {
  return {
    code: 200,
    msg: msg,
    data: data,
  };
};

const errorResponse = (msg = '操作失败', code = 500) => {
  return {
    code: code,
    msg: msg,
    data: null,
  };
};

// ================== 1. GET /api/comment/getComment (获取评论列表) ==================
// 注意：使用 options.url 作为第一个参数，Mock.js 会自动解析查询参数
Mock.mock(/\/api\/comment\/getComment/, 'get', (options) => {
  // Mock.js 会自动解析 URL 参数到 options.query
  // 例如：/api/comment/getComment?type=1&typeId=1&pageNum=1&pageSize=10
  const url = options.url;
  const params = {};
  // 简单的正则匹配，提取 ? 后面的参数
  url.replace(/\?(.*)/, ($1, $2) => {
    $2.split('&').forEach(item => {
      const [key, value] = item.split('=');
      params[key] = decodeURIComponent(value);
    });
  });
  const { type, typeId, pageNum, pageSize } = params;

  // 1. 类型转换（因为 URL 参数都是字符串）
  const pNum = parseInt(pageNum) || 1;
  const pSize = parseInt(pageSize) || 10;
  const t = parseInt(type);
  const tId = parseInt(typeId);

  // 2. 筛选数据 (根据 type 和 typeId 过滤)
  let filteredData = commentList.filter(
    (item) => item.commentType === t && item.typeId === tId
  );

  // 3. 模拟分页
  const total = filteredData.length;
  // 计算起始索引
  const start = (pNum - 1) * pSize;
  const end = start + pSize;
  const pageData = filteredData.slice(start, end);

  // 4. 模拟树形结构（简单版：随机抽取几条评论作为子评论）
  // 这里假设评论 ID 小于 5 的作为子评论，关联到 ID 为 1 的父评论
  pageData.forEach((item) => {
    // 模拟随机子评论
    if (item.commentId % 3 === 0) {
      // 例如，ID 能被3整除的，认为是子评论
      item.parentId = 1; // 指定父ID为1
      item.replyId = 2; // 模拟回复ID
    }
  });

  // 5. 构造后端返回的 PageVO 格式
  const pageVO = {
    page: pageData, // 当前页的数据列表
    total: total, // 总数
  };

  return successResponse(pageVO);
});

// ================== 2. POST /api/comment/auth/add/comment (添加评论) ==================
Mock.mock('/api/comment/auth/add/comment', 'post', (options) => {
  try {
    // POST 请求参数在 body 里
    const body = JSON.parse(options.body);
    // 模拟生成一个 ID 并加入数据库
    const newComment = generateComment({
      ...body,
      commentId: Mock.Random.increment(),
      createTime: new Date().toISOString(),
    });
    commentList.unshift(newComment); // 添加到最前面

    return successResponse('评论成功');
  } catch (error) {
    console.error('Mock 添加评论失败:', error);
    return errorResponse('请求参数错误');
  }
});