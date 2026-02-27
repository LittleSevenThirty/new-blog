import http from "../../utils/http";

export function userFavorite(type: number, typeId: string) {
  return http({
    url: "/favorite/auth/favorite",
    method: "post",
    params: {
      type,
      typeId
    }
  });
}

export function cancelFavorite(type: number, typeId: string) {
  return http({
    url: "/favorite/auth/favorite",
    method: "delete",
    params: {
      type,
      typeId
    }
  });
}

// 是否收藏
export const isFavorite = (type: number, typeId: string) => {
  return http({
    url: '/favorite/whether/favorite',
    method: "get",
    params: {
      type,
      typeId
    }
  });
}