import http from "../../utils/http";
import {ArticleSearch} from "./type.ts";

export function getSearchTitleList():Promise<Array<ArticleSearch>>{
    return http.get("");
}