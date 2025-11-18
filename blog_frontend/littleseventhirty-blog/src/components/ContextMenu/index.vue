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
const websiteStore = useWebsiteStore(); // 获取网站配置（如网站名称）
const router = useRouter(); // 路由实例，用户页面跳转

// 定义核心响应状态，控制菜单响应位置
const isVisible=ref(false); // 菜单显示状态（默认隐藏）
const x=ref(0); // 菜单左上角x坐标位置
const y=ref(0); // 菜单左上角y坐标位置

// 暗色模式检测
const isDarkMode = computed(()=>{
  // 结合tailwindcss的dark类属性器，从而判断html元素是否为暗模式
  return document.documentElement.classList.contains("dark");
})

// 右键触发显示菜单
const showMenu=(event: MouseEvent)=>{
  event.preventDefault(); // 禁止鼠标右键菜单

  // 菜单属性
  const menuWidth=240;  // 菜单宽度
  const submenuWidth=180; // 子菜单宽度
  const menuHeight=370  // 菜单高度

}


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