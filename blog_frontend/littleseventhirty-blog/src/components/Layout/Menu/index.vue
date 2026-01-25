<!-- 布局主展示页组件 -->
<script setup lang="ts">
const props = defineProps({
  // 内容区是否只需要展示父组件内容
  onlyFatherContainer: {
    type: Boolean,
    default: false
  },
  // 是否含有侧边栏
  isSideBar: {
    type: Boolean,
    default: false
  },
  // 上边距
  marginTop: {
    type: String,
    default: '0'
  }
})
</script>

<template>
  <div class="header">
    <slot name="header"></slot>
  </div>
  <div v-if="props.onlyFatherContainer">
    <!-- 个人标识 -->
    <div>
      <slot name="banner"></slot>
    </div>
    <div class="banner_container" style="display: flex;justify-content: center;">
      <!-- 内容 -->
      <div class="content_container" style="width: 100%;max-width:100rem">
        <slot name="content"></slot>
      </div>
      <!-- 侧边栏 -->
      <div class="sideBar_container" v-if="props.isSideBar">
        <slot name="side" />
      </div>
    </div>
  </div>
  <!-- 内容区 -->
  <div class="div_container" v-else :style="'margin-top:' + props.marginTop + ';'">
    <div class="banner_container" style="display: flex;justify-content: center;">
      <!-- 内容 -->
      <div class="content_container" style="width: 100%;max-width:100rem">
        <slot name="content"></slot>
      </div>
      <!-- 侧边栏 -->
      <div class="sideBar_container" v-if="props.isSideBar">
        <slot name="side" />
      </div>
    </div>
  </div>
  <!-- 底部区 -->
  <div>
    <slot name="footer"></slot>
  </div>
</template>

<style lang="scss" scoped>
@use "../../../styles/variable.scss" as *;
@media screen and (max-width: 1000px) {
  .div_container {
    margin: 0;
  }
}

.div_container {
  display: flex;
  justify-content: center;
  padding-bottom: 2rem;
  width: 100%;
  margin: 0 0;
  transition: margin .5s;
}

@media screen and (max-width:910px) {
  .content_container {
    width: 100vw;
    padding: .2rem;
  }
}

.content_container {
  height: 100vh;
  width: 60vw;
  max-width: 70rem;
  padding: 1rem;
  background-color: var(--el-bg-color);
  border: .5rem;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  transition: width 0.5s, padding 0.5s;
}

@media screen and (max-width:1200px) {
  .banner_container {
    margin: 0 5%;
  }
}

@media screen and (max-width:600px) {
  .banner_container {
    margin: 0 2%;
  }
}

.father_container {
  //border: red 1px solid;
  width: 100%;
  margin-bottom: 7rem;
  padding: 0.2rem;
  background-color: white;
  border-radius: $border-radius;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
}

.banner_container {
  margin: 0 10%;
  display: flex;
  height: 100%;
  padding-bottom: 2rem;
  transition: margin 0.5s;
}
</style>