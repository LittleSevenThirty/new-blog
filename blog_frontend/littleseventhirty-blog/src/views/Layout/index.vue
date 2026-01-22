<template>
  <Header />
  <!-- 嵌套路由组件视口 -->
  <div style="min-height: 100vh;">
    <router-view v-slot="{ Component }">
      <Transition name="el-fade-in-linear" mode="out-in">
        <component :is="Component" />
      </Transition>
    </router-view>
  </div>
  <!-- 底部Footer组件展示逻辑,不是树洞就显示 -->
  <template v-if="noDisplayComponent">
    <Footer />
  </template>
  <!-- <BottomRightLayout to-top/> -->
  <BottomRightLayout to-top />
</template>

<script setup lang="ts">
import Header from "../../components/Layout/Header/index.vue";
import Footer from "../../components/Layout/Footer/index.vue";
import BottomRightLayout from "../../components/BottomRightLayout/index.vue";
import { useRoute } from "vue-router";
import { computed } from "vue";

const noDisplayComponent = computed(() => {
  return useRoute().path != "/tree-hole";
})
</script>

<style lang="scss" scoped></style>