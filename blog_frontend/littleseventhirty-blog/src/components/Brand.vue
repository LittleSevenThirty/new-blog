<!-- 展示个人div能力组件 -->
<script setup lang="ts">
import GlitchText from './GlitchText.vue';
import useWebstiInfoStore from '../pinia/store/modules/website.ts';
const websiteInfoStore=useWebstiInfoStore();
</script>

<template>
  <div class="brand_container">
    <div class="brand">
      <!-- 标题 -->
      <GlitchText :text="websiteInfoStore?.webInfo?.websiteName||''"/>
    </div>
  </div>
</template>

<style scoped lang="scss">
  .brand_container{
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    width: 100%;
    height: 100vh;
    position: relative;
    min-height: 10rem;
    color: var(--header-text-color);

    .brand{
      display: flex;
      justify-content: center;
      align-items: center;
      flex-direction: column;
      position:fixed;
      z-index:-1;
      top:15rem
    }
  }
</style>