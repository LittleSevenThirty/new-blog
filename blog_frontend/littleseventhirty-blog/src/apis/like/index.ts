import http from "../../utils/http";

export function cancelLike(type: number, typeId: string) {
  return http({
    url: "/like/auth/like",
    method: "delete",
    params: {
      type,
      typeId
    }
  })
}

export function userLike(type: number, typeId: string) {
  return http({
    url: "/like/auth/like",
    method: "post",
    params: {
      type,
      typeId
    }
  });
}

// 是否点赞
export const isLike = (type: number, typeId?: string) => {
  return http({
    url: '/like/whether/like',
    method: "get",
    params: {
      type,
      typeId
    }
  });
}