<!-- 底部右侧快捷功能布局公用组件 -->
<script setup>
import ReadingMode from  "../ReadingMode/index.vue";
import FullScreen from "../FullScreen/index.vue";
import { defineProps, ref } from 'vue';
const props = defineProps({
  // 提供返回顶端功能
  toTop: {
    type: Boolean,
    default: false,
  },
  // 提供显示页面滚动百分比功能
  scrollPercentage: {
    type: Boolean,
    default: false,
  },
  // 阅读方式功能
  redingMode: {
    type: Boolean,
    default: false,
  },
  // 跳转评论功能
  toComment: {
    type: Boolean,
    default: false,
  }
});

const isContainerVisible = ref(false);

const emits=defineEmits(['ReadingMode'])
</script>

<template>
  <div class="div_cantainer">
    <div class="hide" v-bind:class="{ visible: isContainerVisible }">
      <div v-if="props.redingMode" @click="emits('ReadingMode',true)">
        <ReadingMode/>
      </div>
      <FullScreen/>
    </div>
    <div class="my-4">
      
    </div>
  </div>
</template>

<style lang="scss" scoped>
.div_cantainer {
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  align-items: center;
  width: 60px;
  height: auto;
  z-index: 9999;
  position: fixed;
  bottom: 4rem;
  right: 2rem;
  padding: 0.5rem;
}

// 适配手机端
@media screen and (max-width:768px) {
  .div_cantainer {
    bottom: 0px;
    right: 0.5rem;
  }
}

// 隐藏在右边
.hide {
  transform: translate(90px, 0);
  height: auto;
  opacity: 0;
  transition: all 0.5s;
}

.visible {
  height: auto;
  opacity: 1;
  transform: translate(0, 0);
  transition: all 0.5s;
}
</style>