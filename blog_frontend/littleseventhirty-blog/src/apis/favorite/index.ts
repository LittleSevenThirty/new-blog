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