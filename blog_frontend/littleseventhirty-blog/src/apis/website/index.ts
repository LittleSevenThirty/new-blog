import {AxiosResponse,Axios} from "axios";
import { WebsiteInfo } from "./type";
import http from "../../utils/http.ts";

//  获取站点信息
export function getWebsiteInfo(){
    // 会自动在链接前面添加/api
    return http.get("/websiteInfo/front");
    // return http.get("/website/front");
}

// 获取轮播图列表
export function getSlideshow(){
    return http({
        url:"/banner/slideshow",
        method:"get"
    });
}