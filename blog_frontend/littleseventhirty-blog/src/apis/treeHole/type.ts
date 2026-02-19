import http from "../../utils/http";

export function addTreeHole(content: string) {
  return http({
    url: "/treeHole/auth/addTreeHole",
    method: "post",
    params: JSON.stringify({ content })
  });
}

export function getTreeHoleList() {
  return http({
    url: "/treeHole/treeHoleList",
    method: "get"
  });
}