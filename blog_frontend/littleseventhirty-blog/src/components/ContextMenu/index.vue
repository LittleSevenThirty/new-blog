<template>
  <div class="context-menu">

  </div>
</template>

<script lang="ts" setup>
// 引入Vue3核心API（状态管理、生命周期、DOM更新）、路由、Pinia存储
import { ref, onMounted, onUnmounted, computed, nextTick } from 'vue';
import { useRouter } from 'vue-router';
import useWebsiteStore from "../../pinia/store/modules/website.ts";

// 初始化实例依赖
const websiteStore = useWebsiteStore();
const router = useRouter();

</script>

<style lang="scss" scoped>
// 实现“几乎看不见 + 稍小”快速放大并淡入
.context-menu {
  position: fixed;
  z-index: 100000;
  user-select: none; // 防止用户误拖鼠标使组件内文字高亮
  filter: drop-shadow(0 4px 20px rgba(0, 0, 0, 0.15)); // 组件阴影，增加立体感
  animation: fadeIn 5s ease-out;
}

@keyframes fadeIn {
  0% {
    opacity: 0;
    transform: scale(0.98);
  }

  100% {
    opacity: 1;
    transform: scale(1);
  }
}
</style>