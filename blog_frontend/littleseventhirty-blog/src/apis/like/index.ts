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