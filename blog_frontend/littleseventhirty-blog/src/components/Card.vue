<!-- 侧边栏卡片组件 -->
<script lang="ts" setup>
import SvgIcon from "../components/SvgIcon/index.vue";

const props=defineProps({
  // 是否是目录
  isCatalog:{
    type:Boolean,
    default:false
  }
})
</script>

<template>
  <div v-slide-in class="card" v-bind:style="isCatalog?'postion:relative;z-index:9;':''">
    <div class="title" :style="isCatalog?'position:sticky;top:0;':''">
      <div class="title_text">
        <SvgIcon width="30" height="30" name="announcement"></SvgIcon>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
@use "../styles/variable.scss" as *;

.card{
  background-color: var(--el-bg-color);
  width: $card-width;
  margin: $card-margin;
  border: 1px solid var(--el-border-color);
  // 添加阴影
  box-shadow: 0 0 10px rgba(0,0,0,0.2);
  overflow:hidden;

  .title{
    border-radius: $border-radius $border-radius 0 0;
    background-color: var(--el-bg-color);
    z-index:2;
    display:flex;
    justify-content:space-between;
    align-items: center;
    font-size: 20px;

    .title_text{
      display:flex;
      align-items:center;
    }
  }
}
</style>