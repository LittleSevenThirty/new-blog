<script setup>
import { ref } from 'vue';
import MouseTrail from '../../components/MouseTrail.vue';
</script>

<template>
  <div class="home_container">
    <!-- 鼠标拖尾特效 -->
    <MouseTrail/>
  </div>
</template>

<style lang="scss" scoped>
  .home_container{
    width:100%;
  }

  @media screen and (max-width:910px){
    .home_container{
      width:100vw;
    }
  }
</style>