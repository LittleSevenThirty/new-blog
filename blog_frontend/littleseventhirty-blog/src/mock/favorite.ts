import Mock from 'mockjs';

// ================== 模拟收藏数据存储 ==================
// 这里模拟用户的收藏状态，实际开发中这通常是用户的 ID，这里简化为一个全局状态
// 假设我们模拟用户对某个资源的收藏状态
// 数据结构：{ [type_typeId]: true/false }
let favoriteStorage = {
  // 模拟初始数据，例如类型1 ID为100的文章已收藏
  // '1_100': true
};

// 通用成功返回
const successResponse = (data = null, msg = '操作成功') => {
  return {
    code: 200, // 假设 200 为成功
    msg: msg,
    data: data
  };
};

// 通用错误返回
const errorResponse = (msg = '操作失败', code = 500) => {
  return {
    code: code,
    msg: msg,
    data: null
  };
};

// ================== 收藏接口拦截 ==================
// URL: /favorite/auth/favorite, Method: POST
Mock.mock('/api/favorite/auth/favorite', 'post', (options) => {
  try {
    // POST 请求参数通常在 body 里
    const body = JSON.parse(options.body);
    const type = parseInt(body.type);
    const typeId = body.typeId; // 这里可能是字符串或数字

    if (!type || !typeId) {
      return errorResponse('参数缺失');
    }

    // 生成唯一的键
    const key = `${type}_${typeId}`;

    // 设置为收藏状态
    favoriteStorage[key] = true;

    return successResponse(undefined, '收藏成功');
  } catch (error) {
    return errorResponse('收藏失败');
  }
});

// ================== 取消收藏拦截 ==================
// URL: /favorite/auth/favorite, Method: DELETE
Mock.mock('/api/favorite/auth/favorite', 'delete', (options) => {
  try {
    // DELETE 请求参数也可能在 body 里（取决于 Axios 配置）
    const body = JSON.parse(options.body);
    const type = parseInt(body.type);
    const typeId = body.typeId;

    if (!type || !typeId) {
      return errorResponse('参数缺失');
    }

    const key = `${type}_${typeId}`;

    // 如果存在则取消收藏（删除状态）
    if (favoriteStorage[key]) {
      delete favoriteStorage[key];
      return successResponse(undefined, '取消收藏成功');
    } else {
      // 如果未收藏，也可以返回成功，或者提示未收藏
      return successResponse(undefined, '未收藏，无需取消');
    }
  } catch (error) {
    return errorResponse('取消收藏失败');
  }
});

// ================== 是否收藏拦截 ==================
// URL: /favorite/whether/favorite, Method: GET
Mock.mock(RegExp('/api/favorite/whether/favorite' + '.*'), 'get', (options) => {
  try {
    // GET 请求参数在 query 里
    const { type, typeId } = options.query;

    if (!type || !typeId) {
      return successResponse(false); // 参数不对默认返回未收藏
    }

    const key = `${type}_${typeId}`;

    // 检查收藏状态，默认为 false (未收藏)
    const isFavorited = !!favoriteStorage[key];

    return successResponse(isFavorited);
  } catch (error) {
    // 出错时通常返回未收藏，避免阻塞页面
    return successResponse(false);
  }
});