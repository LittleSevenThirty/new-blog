import http from "../../utils/http";

export function categoryList() {
  return http({
    url: "/category/list",
    method: "get"
  })
}