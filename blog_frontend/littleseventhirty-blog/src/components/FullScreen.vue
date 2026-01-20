<script setup>
import { ref } from 'vue';
import { useFullscreen } from '@vueuse/core';
import { ElMessage } from 'element-plus';
// 绑定整个文档作为全屏目标（实现网页全屏）
const targetRef = ref<HTMLElement>(document.documentElement);

// 使用 VueUse 全屏工具
const { 
  toggle, 
  isLoading 
} = useFullscreen(targetRef, {
  onEnter: () => ElMessage.success('进入网页全屏'),
  onExit: () => ElMessage.success('退出网页全屏'),
});
const fullScreenFunc=async () => {
  // 避免重复点击
  if (isLoading.value) return;

  try {
    // 切换全屏状态
    await toggle();
  } catch (err) {
    console.error('全屏操作失败：', err);
    // 友好提示用户
    ElMessage?.error('当前浏览器不支持全屏功能或权限不足');
  }
};
</script>

<template>
  <div @click="fullScreenFunc()" style="cursor: pointer;">
    <el-tooltip content="网页全屏" placement="right" effect="light">
      <div class="back_to_top">
        <svg t="1768896182380" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
          p-id="5738" width="30" height="30">
          <path
            d="M368.896 192H224a32 32 0 0 0-32 32v137.888a32 32 0 0 0 64 0V256h112.896a32 32 0 0 0 0-64zM784.864 192H640a32 32 0 1 0 0 64h112.864v105.888a32 32 0 1 0 64 0V224a32 32 0 0 0-32-32zM368.896 777.92H256V672a32 32 0 1 0-64 0v137.92a32 32 0 0 0 32 32h144.896a32 32 0 1 0 0-64zM784.864 640a32 32 0 0 0-32 32v105.92H640a32 32 0 1 0 0 64h144.864a32 32 0 0 0 32-32V672a32 32 0 0 0-32-32z"
            fill="#020202" p-id="5739"></path>
          <path
            d="M912 48h-800c-35.296 0-64 28.704-64 64v800c0 35.296 28.704 64 64 64h800c35.296 0 64-28.704 64-64v-800c0-35.296-28.704-64-64-64z m-800 864v-800h800l0.064 800H112z"
            fill="#020202" p-id="5740"></path>
        </svg>
      </div>
    </el-tooltip>
  </div>
</template>

<style scoped lang="scss">
.back_to_top {
  height: 30px !important;
  width: 30px !important;

  @media screen and (max-width:768px) {
    height: 30px !important;
    width: 30px !important;
  }
}
</style>