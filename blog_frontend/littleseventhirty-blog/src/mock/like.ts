import Mock from 'mockjs';

// ================== 模拟数据存储 ==================
// 模拟点赞数据库
// 结构: { 'type_typeId': [ { likeId, userId, type, typeId, createTime }, ... ] }
let likeDatabase = {};

// 生成随机ID
const generateId = () => Mock.Random.increment();

// 通用响应格式
const success = (data = null, msg = '操作成功') => {
  return {
    code: 200,
    msg: msg,
    data: data
  };
};

const error = (msg = '操作失败', code = 500) => {
  return {
    code: code,
    msg: msg,
    data: null
  };
};

// ================== 工具函数：构建唯一键 ==================
const getUniqueKey = (type, typeId) => {
  return `${type}_${typeId}`;
};

// ================== POST /api/like/auth/like (点赞) ==================
Mock.mock('/api/like/auth/like', 'post', (options) => {
  try {
    // 解析 POST 请求体
    const body = JSON.parse(options.body);
    const type = parseInt(body.type);
    const typeId = body.typeId ? parseInt(body.typeId) : null;

    if (!type || !typeId) {
      return error('参数缺失');
    }

    const key = getUniqueKey(type, typeId);

    // 模拟当前用户ID (实际应从 Token 解析，这里模拟为 123)
    const mockUserId = 123;

    // 如果该资源还没有点赞记录，初始化数组
    if (!likeDatabase[key]) {
      likeDatabase[key] = [];
    }

    // 检查用户是否已经点赞 (防止重复点赞，通常前端会禁用按钮，但后端也要校验)
    const hasLiked = likeDatabase[key].some(item => item.userId === mockUserId);
    if (hasLiked) {
      return success(undefined, '已点赞');
    }

    // 创建新的点赞记录
    const newLike = {
      likeId: generateId(),
      userId: mockUserId,
      type: type,
      typeId: typeId,
      createTime: new Date().toISOString(),
      updateTime: new Date().toISOString()
    };

    // 存入数据库
    likeDatabase[key].push(newLike);

    return success(undefined, '点赞成功');
  } catch (err) {
    return error('点赞失败');
  }
});

// ================== DELETE /api/like/auth/like (取消点赞) ==================
Mock.mock('/api/like/auth/like', 'delete', (options) => {
  try {
    const body = JSON.parse(options.body);
    const type = parseInt(body.type);
    const typeId = body.typeId ? parseInt(body.typeId) : null;

    if (!type || !typeId) {
      return error('参数缺失');
    }

    const key = getUniqueKey(type, typeId);
    const mockUserId = 123; // 模拟当前用户

    if (!likeDatabase[key]) {
      return success(undefined, '未点赞');
    }

    // 过滤掉当前用户的点赞记录
    const index = likeDatabase[key].findIndex(item => item.userId === mockUserId);
    if (index !== -1) {
      likeDatabase[key].splice(index, 1);
      // 如果该资源没有点赞人了，可以删除该键
      if (likeDatabase[key].length === 0) {
        delete likeDatabase[key];
      }
      return success(undefined, '取消点赞成功');
    } else {
      return success(undefined, '未点赞');
    }
  } catch (err) {
    return error('取消点赞失败');
  }
});

// ================== GET /api/like/whether/like (是否点赞) ==================
// 注意：后端返回的是 ResponseResult<List<Like>>，即一个点赞记录列表
Mock.mock(RegExp('/api/like/whether/like' + '.*'), 'get', (options) => {
  try {
    // GET 请求参数在 query 中
    const { type, typeId } = options.query;
    const mockUserId = 123; // 模拟当前用户ID

    if (!type || !typeId) {
      // 如果参数不全，返回空数组或 false 逻辑
      // 这里根据后端接口定义，返回的是 List<Like>，所以返回空数组
      return success([]);
    }

    const key = getUniqueKey(type, typeId);

    // 如果数据库中没有该记录，返回空数组
    if (!likeDatabase[key]) {
      return success([]);
    }

    // 通常这个接口可能是查询当前用户对该资源的点赞状态
    // 或者查询所有人的点赞状态（如果是查询所有，则直接返回 likeDatabase[key]）
    // 这里假设接口逻辑是返回当前用户的点赞记录（数组，可能为空）
    const userLikeRecord = likeDatabase[key].filter(item => item.userId === mockUserId);

    return success(userLikeRecord);
  } catch (err) {
    // 出错时返回空数组，避免前端报错
    return success([]);
  }
});