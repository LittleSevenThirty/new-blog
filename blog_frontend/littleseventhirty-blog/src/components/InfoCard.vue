<script lang="ts" setup>
import useWebsiteStore from "../pinia/store/modules/website.ts";

const websiteStore = useWebsiteStore();
</script>

<template>
  <div v-slide-in class="info_container">
    <!-- 背景图片 -->
    <div class="background_image"
      :style="`background_image: url(${websiteStore.webInfo?.webMasterProfileBackground});`"></div>
    <!-- 头像 -->
    <div class="avater_wrapper">
      <el-avater :src="websiteStore.webInfo?.webMasterAvater" size="large" />
    </div>
  </div>
</template>

<scirpt lang="scss" scoped>
@use "../styles/variable.scss" as *;

.info_container {
  box-shadow: border-box;
  width: $card-width;
  min-height: 300px;
  margin: $card-margin;
  border-radius: $border-radius;
  text-align: center;
  border: 1px solid var(--el-border-color-lighter);
  background-color: var(--el-bg-color);
  position: relative;
  overflow: hidden;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  display: flex;
  flex-direction: column;

  &:hover {
    transform: translateY(-5px);
    box-shadow: var(--el-box-shadow-light);
  }

  .background_image {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    height: 67%;
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;

    &::after {
      content: '';
      height: 100%;
      left: 0;
      right: 0;
      bottom: 0;
      position: absolute;
      background: linear-gradient(to bottom, rgba(0, 0, 0, .2) 0%, var(--el-bg-color) 100%);
    }
  }

  .avater_wrapper {
    margin-top: 30px;

    .el-avater {
      width: 90px;
      height: 90px;
      border: 3px solid var(--el-color-white);
      box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
      transition: all 0.6s ease;
    }

    &:hover .el-avater {
      transform: rotate(360deg) scale(1.1);
    }
  }
}

@media screen and (max-width:768px) {
  .info_container {
    width: 100%;
    margin: 10px 0;
  }
}
</scirpt>