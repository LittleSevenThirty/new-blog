<template>
  <svg v-bind:style="{ width, height }">
    <use :xlink:href="props.prefix + props.name" :fill="props.color"></use>
  </svg>
</template>

<script setup lang="ts">
// 父组件参数传递
const props = defineProps({
  // :xlink:href 属性值的前缀
  prefix: {
    type: String,
    default: '#icon-',
  },
  // 接收父组件传递的颜色
  color: {
    type: String,
    default: '',
  },
  // 由父组件定义子组件的名称
  name: String,
  // 父组件传递的图标宽度
  width: {
    type: String,
    default: '16px',
  },
  // 父组件传递的图标高度
  height: {
    type: String,
    default: '16px',
  }
})
</script>

<style scoped lang="scss"></style>