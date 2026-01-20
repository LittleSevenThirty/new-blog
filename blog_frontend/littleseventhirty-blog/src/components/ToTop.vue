<script setup>
import { ref } from 'vue';

const top=ref(0);
// 使用节流函数
window.addEventListener("scroll",throttle(()=>{
  top.value=window.screenY|document.documentElement.scrollTop|document.body.top|0;
},200));
</script>

<template>
  <Transition name="el-zoom-in-bottom">
    <div></div>
  </Transition>
</template>

<style scoped lang="scss"></style>