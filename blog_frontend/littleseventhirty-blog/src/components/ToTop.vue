<script setup>
import { ref } from 'vue';
import { throttle } from '../utils/tools';

const top = ref(0);
// 使用节流函数
window.addEventListener("scroll", throttle(() => {
  top.value = window.screenY | document.documentElement.scrollTop | document.body.top | 0;
}, 200));

const backToTop = () => {
  window.scrollTo({
    top: 0,
    behavior: 'smooth'
  })
}
</script>

<template>
  <Transition name="el-zoom-in-bottom">
    <div class="mb-4" v-if="top >= 1000" @click="backToTop">
      <el-tooltip effect="light" content="返回顶部" placement="right">
        <div class="back_to_top">
          <svg t="1768921006825" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
            p-id="5294" width="30" height="30">
            <path
              d="M877.330034 611.250468 542.062178 273.77841c-7.429203-7.463995-17.135262-11.05273-26.801413-10.978029-1.309832-0.107447-2.426259-0.74599-3.736091-0.74599-5.451152 0-10.527774 1.270946-15.192004 3.361561-4.705162 1.793856-9.18315 4.516874-12.992919 8.328689L180.811554 575.74686c-14.600533 14.559601-14.633279 38.192857-0.039909 52.78725 7.2798 7.315616 16.838503 10.977006 26.429952 10.977006 9.55768 0 19.077497-3.660366 26.396183-10.902304l240.59873-240.222153 0 485.548558c0 20.643156 16.72287 37.332257 37.32714 37.332257 20.609387 0 37.332257-16.690124 37.332257-37.332257L548.855908 386.556986l275.53952 277.328259c7.2798 7.318686 16.912182 11.015891 26.463722 11.015891 9.559727 0 19.042705-3.625574 26.323529-10.862395C891.850749 649.51291 891.889635 625.844861 877.330034 611.250468L877.330034 611.250468zM922.166195 187.393971 100.889293 187.393971c-20.61041 0-37.334303-16.723893-37.334303-37.32714 0-20.608364 16.723893-37.332257 37.334303-37.332257L922.166195 112.734574c20.642133 0 37.32714 16.723893 37.32714 37.332257C959.493336 170.669054 942.808328 187.393971 922.166195 187.393971L922.166195 187.393971zM922.166195 187.393971"
              fill="#272636" p-id="5295"></path>
          </svg>
        </div>
      </el-tooltip>
    </div>
  </Transition>
</template>

<style scoped lang="scss">
.back_to_top {
  height: 30px !important;
  width: 30px !important;

  @media screen and (max-width:768px) {
    height: 30px !important;
    width: 30px !important;
  }
}
</style>