import http from "../../utils/http";
const yiYan=import.meta.env.VITE_YIYAN_API;

export function getSoup(){
  if(!yiYan)return null;
  return http({
    url:yiYan,
    method:'get'
  })
}