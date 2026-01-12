import { AxiosResponse } from "axios";
import http from "../../utils/http";
import { get } from "http";
import { UserInfo } from "./type";

export function getUserInfo():Promise<AxiosResponse<UserInfo>>{
  return http({
    url:"/user/auth/info",
    method:"get"
  });
}