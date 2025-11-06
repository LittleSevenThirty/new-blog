import {AxiosResponse,Axios} from "axios";
import { WebsiteInfo } from "./type";
import http from "../../utils/http.ts";

const axios=new Axios();

function getWebsiteInfo():Promise<AxiosResponse<WebsiteInfo>>{
    return axios.get<WebsiteInfo>("http");
}