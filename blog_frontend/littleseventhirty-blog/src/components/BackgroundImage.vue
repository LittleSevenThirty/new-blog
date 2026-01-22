<!-- 背景轮播图组件 -->
<script lang="ts" setup>
import { onMounted, ref, Ref } from 'vue';
import { getSlideshow } from '../apis/website';
import { slideshowItem } from '../apis/website/type';
const imageList = ref<slideshowItem[]>();

onMounted(async ()=>{
  const res=await getSlideshow();
  imageList.value=res.data;
})
</script>

<template>
  <div class="imgs">
    <ul>
      <li class="items" v-for="( image, index ) in imageList" :key="index"
        :style="{ 'background-image': 'url(' + image + ')' }">
      </li>
    </ul>
  </div>
</template>

<style lang="scss" scoped>
.imgs {
  top: 0;
  left: 0;
  position: fixed;
  width: 100vw;
  height: 100vh;
  background-color: #363636;
  z-index: -10;
  overflow: hidden; // 多余的背景图隐藏

  .items {
    top: 0;
    left: 0;
    position: absolute;
    width: 100%;
    height: 100%;
    background: no-repeat 50% 50% / cover;
    opacity: 0;
    // 一张图片6s
    animation: imageAnimation 30s linear infinite 0s;
    backface-visibility: hidden;
    transform-style: preserve-3d;

    &:nth-child(2) {
      animation-delay: 6s;
    }

    &:nth-child(3) {
      animation-delay: 12s;
    }

    &:nth-child(4) {
      animation-delay: 18s;
    }

    &:nth-child(5) {
      animation-delay: 24s;
    }

    &:nth-child(6) {
      animation-delay: 30s;
    }
  }

  // 蒙版
  &::before {
    content: '';
    display: block;
    position: absolute;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    background-color: rgba(0, 0, 0, 0.2);
    transition: all .2s ease-in-out 0s;
  }
}

@keyframes imageAnimation {
  0% {
    opacity: 0;
    animation-timing-function: ease-in;
  }

  // 显现背景图
  2% {
    opacity: 1;
  }

  8% {
    opacity: 1;
    transform: scale(1.05); // 轻微放大
    animation-timing-function: ease-out;
  }

  17% {
    opacity: 1;
    transform: scale(1.1);
  }

  25% {
    opacity: 0;
    transform: scale(1.1);
  }

  100% {
    opacity: 0;
  }
}
</style>