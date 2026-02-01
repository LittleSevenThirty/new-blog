<!-- 自定义右键菜单组件 -->
<template>
  <div class="context-menu" v-show="isVisible" v-bind:style="{
    top: `${y}px`,
    left: `${x}px`
  }">
    <div class="menu-container"
      v-bind:style="isDarkMode ? 'background-color: #1e1b2d; color: #e2e0e7; border-color: #2d2644;' : 'background-color: #ffffff;'">
      <div class="menu-header"
        v-bind:style="isDarkMode ? 'background: linear-gradient(to right, #312a48, #2d2644);border-color:#372f52;' : 'background: linear-gradient(to right, #f9f5ff, #f5f3ff);'">
        <div class="menu-header-icon">
          <svg t="1763626189359" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
            p-id="5330" width="20" height="20">
            <path
              d="M917.333333 512c0-223.850667-181.482667-405.333333-405.333333-405.333333S106.666667 288.149333 106.666667 512s181.482667 405.333333 405.333333 405.333333 405.333333-181.482667 405.333333-405.333333z m-425.984-5.333333a21.333333 21.333333 0 0 0 6.101334 20.928l124.8 116.394666a21.333333 21.333333 0 0 0 29.12-31.189333l-118.08-110.122667L533.333333 501.333333v-170.666666a21.333333 21.333333 0 0 0-42.666666 0v170.666666c0 1.834667 0.213333 3.626667 0.682666 5.333334zM149.333333 512c0-200.298667 162.368-362.666667 362.666667-362.666667s362.666667 162.368 362.666667 362.666667-162.368 362.666667-362.666667 362.666667S149.333333 712.298667 149.333333 512z m612.501334 305.002667a21.333333 21.333333 0 1 0-30.336 29.994666l74.304 75.157334a21.333333 21.333333 0 1 0 30.336-29.994667l-74.304-75.157333z m-480.362667 29.994666a21.333333 21.333333 0 0 0-30.336-29.994666L176.832 892.16a21.333333 21.333333 0 1 0 30.336 29.994667l74.304-75.157334zM778.496 128A117.525333 117.525333 0 0 1 896 245.333333a21.333333 21.333333 0 0 0 42.666667 0C938.666667 157.077333 866.901333 85.333333 778.496 85.333333h-20.992a21.333333 21.333333 0 1 0 0 42.666667h20.992zM245.504 85.333333C157.098667 85.333333 85.333333 157.077333 85.333333 245.333333a21.333333 21.333333 0 0 0 42.666667 0C128 180.629333 180.672 128 245.504 128h20.992a21.333333 21.333333 0 1 0 0-42.666667h-20.992z"
              stroke-width="60" stroke="black" fill="#3D3D3D" p-id="5331"></path>
          </svg>
        </div>
        <span :style="isDarkMode ? 'color: #a78bfa;' : 'color: #8b5cf6;'">{{ websiteStore.webInfo?.websiteName }}</span>
      </div>
      <ul class="menu-items">
        <li class="menu-item" v-on:click="navigateTo('/')">
          <div class="menu-item-icon">
            <svg viewBox="0 0 24 24" width="24" height="24" :stroke="isDarkMode ? '#a78bfa' : '#8b5cf6'"
              stroke-width="2" fill="none" stroke-linecap="round" stroke-linejoin="round">
              <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path>
              <polyline points="9 22 9 12 15 12 15 22"></polyline>
            </svg>
          </div>
        </li>
      </ul>
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

// 隐藏（关闭）菜单
const hideMenu = () => {
  isVisible.value = false;
}

// 跳转页面
const navigateTo = (path: string) => {
  console.log("证明我被点击了" + path);
  router.push(path);
  hideMenu();
}

onMounted(() => {
  document.addEventListener('contextmenu', showMenu);
})
</script>

<style lang="scss" scoped>
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

html[class="dark"] .menu-container {
  // 背景，字体，边框，阴影
  background-color: #25234a;
  border: 1px solid #1a1835;
  color: #f2f2f6;
}

.menu-header {
  // 内边距，下外边界，下边框，左右上的圆角，弹性布局-内居中，定位
  position: relative;
  padding: 14px 16px;
  margin-bottom: 1px;
  border-bottom: 1px solid #f9f9fc;
  border-top-left-radius: 12px;
  border-top-right-radius: 12px;
  display: flex;
  align-items: center;
  background: linear-gradient(to right, hsl(240, 5%, 78%), lch(96.94% 1.03 290.33));
}

html[class="dark"] .menu-header {
  background: linear-gradient(to right, #312a48, #2d2644);
  border: 1px solid #312a48;
}

.menu-header-icon {
  width: 20px;
  height: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-right: 10px;
  animation: alarm-clock 1s infinite;
}

/* 2. 优化闹钟 */
@keyframes alarm-clock {
  0% {
    transform: rotate(0deg) scale(1);
  }

  /* 初始状态 */
  12% {
    transform: rotate(1.5deg) scale(1.015)
  }

  25% {
    transform: rotate(3deg) scale(1.03);
  }

  /* 顺时针摇晃+小幅放大 */
  37% {
    transform: rotate(1.5deg) scale(1.05)
  }

  50% {
    transform: rotate(0deg) scale(1.07);
  }

  /* 回到中间+最大放大 */
  62% {
    transform: rotate(-1.5deg) scale(1.05)
  }

  75% {
    transform: rotate(-3deg) scale(1.03);
  }

  /* 逆时针摇晃+小幅放大 */
  87% {
    transform: rotate(1.5deg) scale(1.015)
  }

  100% {
    transform: rotate(0deg) scale(1);
  }

  /* 回到初始状态 */
}

.menu-items {
  list-style: none;
  margin: 0;
  padding: 10px;
}

.menu-item {
  // 定位，布局，溢出文字，字体大小，外边距，四角，鼠标样式，
  position: relative;
  display: block;
  font-size: 0.9rem;
  overflow: visible !important;
  margin: 4px 0;
  border-radius: 10px;
  cursor: pointer;
  transition: all 0.2s ease;
}

.menu-item-icon {
  // 布局，高宽，右边距，内部物体放置，本身排列，
  display: flex;
  width: 18px;
  height: 18px;
  margin-right: 12px;
  align-items: center;
  justify-content: center;
  transition: transform 0.3s;
}
</style>