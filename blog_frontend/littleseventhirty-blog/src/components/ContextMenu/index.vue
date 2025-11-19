<template>
  <div class="context-menu" v-bind:style="{
    top: `${y}px`,
    left: `${x}px`
  }">
    <div class="menu-container"
      v-bind:style="isDarkMode ? 'background-color: #1e1b2d; color: #e2e0e7; border-color: #2d2644;' : 'background-color: #ffffff;'">
      <div class="menu-header" v-bind:style="isDarkMode?'':''">

      </div>
      <ul class="menu-items"></ul>
    </div>
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
const isVisible = ref(false); // 菜单显示状态（默认隐藏）
const x = ref(0); // 菜单左上角x坐标位置
const y = ref(0); // 菜单左上角y坐标位置

// 暗色模式检测
const isDarkMode = computed(() => {
  // 结合tailwindcss的dark类属性器，从而判断html元素是否为暗模式
  return document.documentElement.classList.contains("dark");
})

// 右键触发显示菜单
const showMenu = (event: MouseEvent) => {
  event.preventDefault(); // 禁止鼠标右键菜单

  // 菜单属性
  const menuWidth = 240;  // 菜单宽度
  const submenuWidth = 180; // 子菜单宽度
  const menuHeight = 370  // 菜单高度

  // 获取鼠标位置，计算菜单存放位置
  let posX = event.clientX;
  let posY = event.clientY;
  // console.log(posY);  // 屏幕最高594

  // 判断是否可以放下自定义右键菜单
  // 超出右边界，往左移移
  if (posX + menuWidth + submenuWidth > window.innerWidth) {
    posX = Math.max(10, window.innerWidth - menuWidth - 10);
  }

  // 超出下边界，往上移移
  if (posY + menuHeight > window.innerHeight) {
    posY = Math.max(10, window.innerHeight - menuHeight - 10);
  }

  // 调整菜单显现位置
  x.value = posX;
  y.value = posY;
  isVisible.value = true;

  // 关闭所有可能打开的子菜单

  // 调节子菜单显示位置
  nextTick(() => { });
}

onMounted(() => {
  document.addEventListener('contextmenu', showMenu);
})
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

.menu-container {
  position: relative;
  min-width: 240px;
  min-height: 240px;
  background-color: #ffffff;
  border: 1px solide #f3f0ff;
  border-radius: 12px;
  color: #414a58;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Arial, sans-serif;
  box-shadow: 0 4px 20px rgba(139, 92, 246, 0.15);
  transition: all 0.2s ease;
  animation: slideIn 0.2s ease;
  overflow: visible !important; // 字体超出显示效果
}

@keyframes slideIn {
  from {
    opacity: 0;
    transform: translateX(5px);
  }

  to {
    opacity: 1;
    transform: translate(0px);
  }
}
</style>