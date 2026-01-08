import {AxiosResponse,Axios} from "axios";
import { WebsiteInfo } from "./type";
import http from "../../utils/http.ts";

//  获取站点信息
export function getWebsiteInfo(){
    // 会自动在链接前面添加/api
    return http.get("/website/front");
    // return http.get("/website/front");
}

// export async function