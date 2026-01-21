<!-- 底部右侧快捷功能布局公用组件 -->
<script setup>
import ReadingMode from "../ReadingMode.vue";
import FullScreen from "../FullScreen.vue";
import BottomRightMore from "../BottomRightMore.vue";
import ToTop from "../ToTop.vue";
import ToComment from "../ToComment.vue";
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

console.log(props.toTop);

const isContainerVisible = ref(false);

const toggleCantainer = () => {
  isContainerVisible.value = !isContainerVisible.value;
}

const emits = defineEmits(['ReadingMode'])
</script>

<template>
  <div class="div_cantainer">
    <div class="hide" v-bind:class="{ visible: isContainerVisible }">
      <div v-if="props.redingMode" @click="emits('ReadingMode', true)">
        <ReadingMode />
      </div>
      <FullScreen />
    </div>
    <div class="my-4" @click="toggleCantainer">
      <BottomRightMore />
    </div>
    <div v-if="props.toTop">
      <ToTop />
    </div>
    <div class="mb-4" v-if="props.toComment">
      <ToComment />
    </div>
    <!-- <div class="scroll_percentage" v-if="props.scrollPercentage"> -->
      <!-- 提供插槽，后期填入对应百分比显示条 -->
      <!-- <slot name="scroll_percentage"></slot> -->
    <!-- </div> -->
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

  .scroll_percentage {
    background: var(--mao-scroll-percentage-bg);
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
    border-radius: 10px;
    width: 40px;
    height: 40px;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: .9rem;
    font-weight: bold;
  }
}

// 适配手机端
@media screen and (max-width:768px) {
  .div_cantainer {
    bottom: 0px;
    right: 0.5rem;
  }

  .scroll_percentage {
    width: 40px;
    height: 40px;
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