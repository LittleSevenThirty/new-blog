<!-- 侧边栏卡片组件 -->
<script lang="ts" setup>
import SvgIcon from "../components/SvgIcon/index.vue";

const props = defineProps({
  // 是否是目录
  isCatalog: {
    type: Boolean,
    default: false
  },
  // 前图标名字
  prefixIcon: String,
  // 后图标名字
  suffixIcon: {
    type: String,
    default: ''
  },
  // 是否抖动
  isDithering: {
    type: Boolean,
    default: false
  },
  // 是否循环缩放
  isScale: {
    type: Boolean,
    default: false
  },
  title: String,
  // 展示箭头
  isArrow: {
    type: Boolean,
    default: false
  },
  // 是否旋转
  isRotate: {
    type: Boolean,
    default: false
  }
});

const emits = defineEmits(["invoke"]);

function invoke() {
  emits("invoke");
}
</script>

<template>
  <div v-slide-in class="card" v-bind:style="isCatalog ? 'postion:relative;z-index:9;' : ''">
    <div class="title" :style="isCatalog ? 'position:sticky;top:0;' : ''">
      <div class="title_text">
        <SvgIcon :class="{ 'dithering': props.isDithering, 'scale': props.isScale }" width="30" height="30"
          v-bind:name="props.prefixIcon">
        </SvgIcon>
        <div style="margin-left:10px">{{ props.title }}</div>
      </div>
      <!-- 提示信息 -->
      <el-tooltip class="box-item" content="刷新" placement="top" effect="light" v-if="suffixIcon == 'rotate'">
        <div :class="{ 'arrow': props.isArrow, 'rotate': props.isRotate }" style="cursor:pointer;" @click="invoke()">
          <SvgIcon :name="props.suffixIcon" width="30" height="30"></SvgIcon>
        </div>
      </el-tooltip>
      <div v-else :class="{ 'arrow': props.isArrow, 'rotate': props.isRotate }" @click="invoke()">
        <SvgIcon :name="props.suffixIcon" width="30" height="30"></SvgIcon>
      </div>
      <!-- 标题块结束 -->
    </div>
    <div class="content">
      <slot name="content"/>
    </div>
  </div>
</template>

<style scoped lang="scss">
@use "../styles/variable.scss" as *;

.card {
  background-color: var(--el-bg-color);
  width: $card-width;
  margin: $card-margin;
  border: 1px solid black;
  // 添加阴影
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  overflow: hidden;
  border-radius: $border-radius 0 $border-radius 0;

  .title {
    border-radius: $border-radius $border-radius 0 0;
    background-color: var(--el-bg-color);
    z-index: 2;
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 20px;

    .title_text {
      display: flex;
      align-items: center;

      // 左右抖动
      .dithering {
        animation: shake 0.5s infinite;
        transform-origin: center; // 变形的原点
      }

      // 循环缩放
      .scale {
        animation: scale 0.5s infinite;
        transform-origin: center;
      }
    }

    .arrow:hover{
      animation: move 1s infinite;
    }

    .rotate:hover{
      animation: rotate 1s infinite linear;
    }
  }

  .content{
    min-height: 5em;
    text-align: center;
    line-height: 22px;
    padding:10px;
    color:grey;
  }
}

@keyframes shake {
  0% {
    transform: rotate(0deg);
  }

  25% {
    transform: rotate(-10deg);
  }

  50% {
    transform: rotate(0deg);
  }

  75% {
    transform: rotate(10deg);
  }

  100% {
    transform: rotate(0deg);
  }
}

@keyframes scale {
  0% {
    transform: scale(1);
  }

  50% {
    transform: scale(1.1);
  }

  100% {
    transform: scale(1);
  }
}

@keyframes move {
  0%{
    transform:translateX(0);
  }
  50%{
    transform: translateX(5px);
  }
  100%{
    transform: translateX(0);
  }
}

@keyframes rotate{
  0%{
    transform: rotate(0deg);
  }
  100%{
    transform:rotate(360deg);
  }
}
</style>