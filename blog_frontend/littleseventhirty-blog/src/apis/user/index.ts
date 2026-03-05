import { AxiosResponse } from "axios";
import http from "../../utils/http";
import { UserInfo } from "./type";

// 获取登录用户信息
export function getUserInfo(): Promise<AxiosResponse<UserInfo>> {
  return http({
    url: "/user/auth/info",
    method: "get"
  });
}

// 登录
export function login(data: object) {
  return http({
    url: "/user/login",
    method: "post",
    data: data,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
    }
  });
}

// 退出登录
export function logout() {
  return http({
    url: "/user/logout",
    method: "post"
  });
}

// 修改用户信息
export function updateUserAccount(data: any) {
  return http({
    url: '/user/auth/update',
    data: data,
    method: 'post'
  })
}

// 修改电子邮箱
export function updateEmail(data: any) {
  return http({
    url: '/user/auth/update/email',
    data: data,
    method: 'post'
  })
}

// 修改第三方登录电子邮箱
export function updateThirdEmail(data: any) {
  return http({
    url: '/user/auth/third/update/email',
    data: data,
    method: 'post'
  })
}