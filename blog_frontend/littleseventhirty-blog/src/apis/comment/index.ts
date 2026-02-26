import http from "../../utils/http";

export function getComment(type: number, typeId: number, pageNum: string, pageSize: string) {
  // 返回后端请求数据
  return http({
    url: "/comment/getComment",
    method: "get",
    params: {
      type,
      typeId,
      pageNum,
      pageSize
    }
  })
}

export function addComment(data: object) {
  return http({
    url: '/comment/auth/add/comment',
    method: "post",
    params: {
      data
    }
  });
}