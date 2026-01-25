<script lang="ts" setup>
import Menu from "../../../components/Layout/Menu/index.vue";
import useWebsiteStore from "../../../pinia/store/modules/website";
import RecommendArticle from "../RecommendArticle/index.vue";

const websiteStore = useWebsiteStore();
</script>

<template>
  <Menu is-side-bar>
    <!-- 内容区 -->
    <template #content>
      <!-- 通知板块 -->
      <div class="announcement">
        <svg t="1769326067129" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
          p-id="1603" width="30" height="30">
          <path
            d="M661.9 299.4c-0.5-0.2-1.1-0.4-1.7-0.5-0.2-0.1-0.4-0.1-0.6-0.2-0.4-0.1-0.8-0.2-1.2-0.4l-0.2-0.1v0.1c-16.5-3.7-33.1 5.9-37.8 22.1-4.7 16.2 4.2 33.2 20 39l-0.3 1.2c80.7 23.5 126.1 111.6 101.3 196.7-14.8 50.7-51.1 88.9-95.1 106.6-10.9 2-20.4 10.3-23.9 22.1-4.8 16.4 4 33.4 19.5 37.9a28.4 28.4 0 0 0 16.6-0.3l0.3 1c65.1-23.1 119.3-78 140.8-151.8 34.4-117.6-27.2-239.1-137.7-273.4z"
            fill="#409EFF" p-id="1604"></path>
          <path
            d="M598.3 379.6c-1.2-0.4-2.4-0.7-3.7-1-0.5-0.1-1-0.2-1.5-0.2-1-0.2-2.1-0.4-3.1-0.6l-0.1 0.4c-13.1-0.2-24.7 9-27.2 22.3s5.1 26.1 17.3 30.6l-0.2 1.1c0.4 0 0.7 0.1 1.1 0.2 44 8.3 73 50.7 64.7 94.7-6.3 33.5-32.3 58.2-63.8 64.5v0.2c-10.5 2.2-19.2 10.5-21.3 21.7-2.8 14.8 7 29.1 21.8 31.9 3.8 0.7 7.7 0.6 11.2-0.2l0.1 0.6c52.1-11.3 94.9-52.7 105.4-108.3 13.5-71.6-31.2-140.4-100.7-157.9z"
            fill="#409EFF" p-id="1605"></path>
          <path
            d="M339.7 383.8H235.2c-11.6 0-21.2 9.5-21.2 21.2v213.8c0 11.6 9.5 21.2 21.2 21.2h104.5c1.3 0 2.4-1.1 2.4-2.4V576h-42.8c-11.6 0-21.2-9.5-21.2-21.2v-85.5c0-11.7 9.5-21.2 21.2-21.2h42.8v-62c0-1.2-1.1-2.3-2.4-2.3zM535.2 278.1c0-10-6.1-16.7-11.7-13l-181.2 120v61.4l139.2-90.1v309l-139.2-87.6v63.5L524 761.4c5.6 3.7 11.7-3.1 11.7-13.1l-0.5-470.2z"
            fill="#409EFF" p-id="1606"></path>
        </svg>
        <span>{{ websiteStore.webInfo?.headerNotification }}</span>
      </div>
      <!-- 推荐文章 -->
      <RecommendArticle />
    </template>
    <!-- 侧边栏 -->
    <template #side></template>
  </Menu>
</template>

<style scoped lang="scss">
.announcement {
  display: flex;
  justify-content: center;
  align-content: center;
  text-align: center;
  min-height: 50px;
  margin-top: 10px;
  border-radius: 15px;
  padding: 5px;
  border: 1px dotted #ccc;
}
</style>