import http from "../../utils/http";


export function addNewLeaveWord(content: string) {
  return http({
    url: "/leaveWord/auth/userLeaveWord",
    method: "post",
    data: content
  });
}

export function getLeaveWordList(id?: any) {
  return http({
    url: "/leaveWord/list",
    method: "get",
    params: {
      id
    }
  })
}