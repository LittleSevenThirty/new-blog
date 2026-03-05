<script setup lang="ts">
import ContextMenu from "./components/ContextMenu/index.vue";
import Loading from "./components/Loading/index.vue";
import useWebsiteStore from "./pinia/store/modules/website.ts";
import { useColorMode, useDark, useToggle } from "@vueuse/core";
import { onMounted } from "vue";

const websiteStore = useWebsiteStore();

onMounted(() => {
  console.log('App组件挂载，开始获取网站信息');
  websiteStore.getInfo();
})

//  深色切换
useDark({
  selector: 'html',
  attribute: 'class',
  valueLight: 'light',
  valueDark: 'dark'
})

useDark({
  onChanged(dark) {
    useToggle(dark)
  }
})
</script>

<template>
  <div>
    <router-view></router-view>
  </div>
  <!-- <HelloWorld msg="世界，你好"/> -->
  <!-- hello 看到我就说明显示没啥问题，但控制台还是得检查检查 -->
  <loading></loading>
  <!-- 全局加载 -->
  <!-- 1. 音乐组件 -->
  <!-- <Music/> -->
  <!-- 2. 阻拦开发者工具查看的组件 -->
  <!-- <DevToolsBlocker/> -->
  <!-- 3. 内容菜单组件 -->
  <ContextMenu />
</template>

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}

.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}

.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>
