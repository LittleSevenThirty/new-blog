import http from "../../utils/http.ts";

export function getTagList() {
  return http({
    url: "/tag/list",
    method: "get"
  });
}