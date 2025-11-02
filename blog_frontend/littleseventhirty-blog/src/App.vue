<script setup lang="ts">
// @ts-ignore
import useWebsiteStore from "@/pinia/store/modules/website.ts";

const websiteStore=useWebsiteStore();

// @ts-ignore
onMounted(()=>{

});
</script>

<template>
  <div>
    <router-view></router-view>
  </div>
  <loading></loading>
  <!-- 全局加载 -->
  <!-- <loading></loading> -->
  <!-- 1. 音乐组件 -->

  <!-- 2. 阻拦开发者工具查看的组件 -->

  <!-- 3. 内容菜单组件 -->
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
