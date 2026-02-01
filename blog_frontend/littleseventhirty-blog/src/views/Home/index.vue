<script setup>
import { ref } from 'vue';
import MouseTrail from '../../components/MouseTrail.vue';
import BackgroundImage from '../../components/BackgroundImage.vue';
import Particles from '../../components/Particles.vue';
import Brand from './Brand/index.vue';
import ScrollParallel from '../../components/ScrollParallel/index.vue';
import Display from './Display/index.vue';
</script>

<template>
  <div class="home_container">
    <!-- 鼠标拖尾特效 -->
    <MouseTrail />
    <!-- 背景图组件 -->
    <BackgroundImage />
    <!-- 粒子背景 -->
    <Particles />
    <!-- 个人标志展示 -->
    <Brand />
    <!-- 平行滚动组件 -->
    <ScrollParallel :speed="0.1" direction="vertical">
      <template v-slot:default>
        <Display />
      </template>
    </ScrollParallel>
  </div>
</template>

<style lang="scss" scoped>
.home_container {
  width: 100%;
}

@media screen and (max-width:910px) {
  .home_container {
    width: 100vw;
  }
}
</style>