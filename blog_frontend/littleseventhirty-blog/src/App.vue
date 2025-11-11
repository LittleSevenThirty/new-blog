<script setup lang="ts">
// import useWebsiteStore from "./pinia/store/modules/website.ts";
import HelloWorld from "./components/HelloWorld.vue";
import http from "./utils/http";

// const websiteStore=useWebsiteStore();

// http.get("https://jsonplaceholder.typicode.com/posts").then(res=>{
//   console.log(res);
// })
</script>

<template>
  <div>
    <router-view></router-view>
  </div>
  <HelloWorld msg="世界，你好"/>
  hello 看到我就说明显示没啥问题，但控制台还是得检查检查
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
