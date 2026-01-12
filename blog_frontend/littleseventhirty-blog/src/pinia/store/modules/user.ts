import { defineStore } from "pinia";
import { GET_TOKEN } from "../../../utils/auth";
import { shallowRef } from "vue";
import { UserInfo } from "../../../apis/user/type";
import { getUserInfo } from "../../../apis/user";

const useUserStore = defineStore("user", () => {
  const token=GET_TOKEN();
  const userInfo=shallowRef<UserInfo>();

  // 获取用户信息
  async function getInfo(){
    const res=await getUserInfo();
    userInfo.value=res.data;
  }

  return {
    token,
    userInfo,
    getInfo
  }
});

export default useUserStore;