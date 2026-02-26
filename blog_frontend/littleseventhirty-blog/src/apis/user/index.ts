import { AxiosResponse } from "axios";
import http from "../../utils/http";
import { get } from "http";
import { UserInfo } from "./type";

// 获取登录用户信息
export function getUserInfo():Promise<AxiosResponse<UserInfo>>{
  return http({
    url:"/user/auth/info",
    method:"get"
  });
}

// 登录
export function login(username: string, password: string){
  return http({
    url:"/user/login",
    method:"post",
    params: {
      username,
      password
    }
  });
}

// 退出登录
export function logout(){
  return http({
    url:"/user/logout",
    method:"post"
  });
}