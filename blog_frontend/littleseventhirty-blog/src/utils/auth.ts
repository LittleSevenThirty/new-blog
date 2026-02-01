import { json } from "stream/consumers";
import { TOKEN_KEY } from "../const/Jwt";
import useUserStore from "../pinia/store/modules/user";
import { ElMessage } from "element-plus";

// 获取token
export function GET_TOKEN() {
  const str = localStorage.getItem(TOKEN_KEY) || sessionStorage.getItem(TOKEN_KEY);
  if (!str) return null; // token值为空
  // 解析token
  const authObject = JSON.parse(str);
  // 判断token是否过期
  if(new Date(authObject.expire)<=new Date()){
    REMOVE_TOKEN();
    ElMessage.warning("登录状态已过期，请重新登录")
    return null;
  }
  return authObject.token;
}

// 设置token
export function SET_TOKEN(token: string, expire: string, remeber: boolean) {
  const userStore = useUserStore();
  const authObject = { token, expire };
  const authStr=JSON.stringify(authObject);
  // 是否记住token
  remeber?localStorage.setItem(TOKEN_KEY,authStr):sessionStorage.setItem(TOKEN_KEY,authStr);
  userStore.token=token;
}

// 删除token
export function REMOVE_TOKEN(){
  localStorage.removeItem(TOKEN_KEY);
  sessionStorage.removeItem(TOKEN_KEY);
}