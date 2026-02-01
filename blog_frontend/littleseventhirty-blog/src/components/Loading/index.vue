<template>
    <div class="loading" v-show="loadingStore.isLoading">
        <!-- 整个屏幕大的盒子 -->
        <div class="load-box">
            <!-- 中间一个小盒子 -->
            <!-- <img>我需要一张图片</img> -->
            <div class="load-circle"></div>
            <!-- 最后一个盒子 -->
            <span>&nbsp;&nbsp;正在带着数据走向你~~</span>
        </div>
    </div>
</template>

<script lang="ts" setup>
import useLoadingStore from '../../pinia/store/modules/loading';
const loadingStore=useLoadingStore();
</script>

<style lang="scss" scoped>
.loading {
  z-index: 999;
  width: 100%;
  height: 100%;
  background: var(--el-bg-color);
  position: fixed;
  top: 0;
  left: 0;
  display: flex;
  justify-content: center;
  align-items: center;

  .load-box {
    display: flex;
    flex-direction: row; // cloumn垂直布局，row水平布局
    justify-content: center; // 水平居中
    align-items: center;

    .load-circle {
      width: 1rem;
      height: 1rem;
      border: 2px solid #fff;
      border-top-color: transparent;
      border-radius: 100%;

      animation: circle infinite 1s linear;
    }

    span {
      font-size: 1rem;
      font-weight: bold;
    }

    @keyframes circle {
      0% {
        transform: rotate(0);
      }

      100% {
        transform: rotate(360deg);
      }
    }
  }
}
</style>