import http from "../../utils/http.ts";

export function getRandomArticle() {
  return http({
    url: "/article/random",
    method: "get"
  })
}