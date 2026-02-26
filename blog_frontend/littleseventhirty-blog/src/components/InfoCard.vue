<script lang="ts" setup>
import useWebsiteStore from "../pinia/store/modules/website.ts";

const websiteStore = useWebsiteStore();

console.log("infoCard");
console.log(websiteStore.webInfo?.webMasterProfileBackground);
</script>

<template>
  <div v-slide-in class="info_container">
    <!-- 背景图片 -->
    <div class="background_image"
      :style="`background-image: url(${websiteStore.webInfo?.webMasterProfileBackground});`"></div>
    <!-- 头像 -->
    <div class="avatar_wrapper">
      <el-avatar :src="websiteStore.webInfo?.webMasteravatar" size="large" />
    </div>
    <!-- 站点信息 -->
    <div class="webmaster_name">
      {{ websiteStore.webInfo?.webMasterName }}
      <div class="title_decorate">
        <div class="line"></div>
        <div class="dot"></div>
        <div class="line"></div>
      </div>
    </div>
    <!-- 个人简介 -->
    <div class="webmaster_copy">
      <el-tooltip v-bind:content="websiteStore.webInfo?.webMasterCopy" placement="bottom" :show-after="500">
        <span>{{ websiteStore.webInfo?.webMasterCopy }}</span>
      </el-tooltip>
    </div>
    <!-- 内容数目 -->
    <div class="quantity_container">
      <!-- 文章 -->
      <div class="quantity_item">
        <div class="number">{{ websiteStore.webInfo?.articleCount }}</div>
        <div class="label">文章数</div>
      </div>
      <!-- 分割线 -->
      <div class="divider"></div>
      <!-- 分类 -->
      <div class="quantity_item">
        <div class="number">{{ websiteStore.webInfo?.categoryCount }}</div>
        <div class="label">分类数</div>
      </div>
      <!-- 分割线 -->
      <div class="divider"></div>
      <!-- 评论 -->
      <div class="quantity_item">
        <div class="number">{{ websiteStore.webInfo?.commentCount }}</div>
        <div class="label">评论数</div>
      </div>
    </div>
    <!-- 图标容器 -->
    <div class="icon_container">
      <a :href="websiteStore.webInfo?.githubLink" target="_blank">
        <div class="icon_wrapper">
          <el-icon>
            <svg t="1770009130641" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
              p-id="4755" width="20" height="20">
              <path
                d="M511.6 76.3C264.3 76.2 64 276.4 64 523.5 64 718.9 189.3 885 363.8 946c23.5 5.9 19.9-10.8 19.9-22.2v-77.5c-135.7 15.9-141.2-73.9-150.3-88.9C215 726 171.5 718 184.5 703c30.9-15.9 62.4 4 98.9 57.9 26.4 39.1 77.9 32.5 104 26 5.7-23.5 17.9-44.5 34.7-60.8-140.6-25.2-199.2-111-199.2-213 0-49.5 16.3-95 48.3-131.7-20.4-60.5 1.9-112.3 4.9-120 58.1-5.2 118.5 41.6 123.2 45.3 33-8.9 70.7-13.6 112.9-13.6 42.4 0 80.2 4.9 113.5 13.9 11.3-8.6 67.3-48.8 121.3-43.9 2.9 7.7 24.7 58.3 5.5 118 32.4 36.8 48.9 82.7 48.9 132.3 0 102.2-59 188.1-200 212.9 23.5 23.2 38.1 55.4 38.1 91v112.5c0.8 9 0 17.9 15 17.9 177.1-59.7 304.6-227 304.6-424.1 0-247.2-200.4-447.3-447.5-447.3z"
                p-id="4756"></path>
            </svg>
          </el-icon>
        </div>
      </a>
      <a :href="websiteStore.webInfo?.giteeLink" target="_blank">
        <div class="icon_wrapper">
          <el-icon>
            <svg t="1770009169742" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
              p-id="5755" width="20" height="20">
              <path
                d="M512 1024C230.4 1024 0 793.6 0 512S230.4 0 512 0s512 230.4 512 512-230.4 512-512 512z m259.2-569.6H480c-12.8 0-25.6 12.8-25.6 25.6v64c0 12.8 12.8 25.6 25.6 25.6h176c12.8 0 25.6 12.8 25.6 25.6V608c0 41.6-35.2 76.8-76.8 76.8h-240c-12.8 0-25.6-12.8-25.6-25.6V416c0-41.6 35.2-76.8 76.8-76.8h355.2c12.8 0 25.6-12.8 25.6-25.6v-64c0-12.8-12.8-25.6-25.6-25.6H416c-105.6 0-188.8 86.4-188.8 188.8V768c0 12.8 12.8 25.6 25.6 25.6h374.4c92.8 0 169.6-76.8 169.6-169.6V480c0-12.8-12.8-25.6-25.6-25.6z"
                p-id="5756"></path>
            </svg>
          </el-icon>
        </div>
      </a>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@use "../styles/variable.scss" as *;

.info_container {
  box-shadow: border-box;
  // width: $card-width;
  width: auto;
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
      z-index: -1;
    }
  }

  >* {
    position: relative;
    z-index: 2;
  }

  .avatar_wrapper {
    margin-top: 30px;

    .el-avatar {
      width: 90px;
      height: 90px;
      border: 3px solid var(--el-color-white);
      box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
      transition: all 0.6s ease;
    }

    &:hover .el-avatar {
      transform: rotate(360deg) scale(1.1);
    }
  }

  .webmaster_name {
    font-size: 1.4rem;
    font-weight: 600;
    margin: 15px 0 10px;
    color: var(--el-text-color-primary);
    padding: 0 20px;

    .title_decorate {
      display: flex;
      align-items: center;
      justify-content: center;
      margin-top: 8px;

      .line {
        height: 1px;
        width: 15px;
        background-color: var(--el-border-color);
        transition: all 0.3s ease;
      }

      .dot {
        width: 4px;
        height: 4px;
        border-radius: 50%;
        background-color: var(--el-color-primary);
        margin: 0 4px;
      }
    }

    &:hover {
      cursor: pointer;

      .line {
        width: 45px;
        background-color: var(--el-bg-color);
      }
    }
  }

  .webmaster_copy {
    color: var(--el-text-color-secondary);
    font-size: 0.95rem;
    line-height: 1.6;
    padding: 0 25px;
    margin-bottom: 20px;
    position: relative;

    &::before,
    &::after {
      content: '"';
      position: absolute;
      font-size: 1.2em;
      color: var(--el-color-primary-light-5);
      opacity: 0.6;
    }
  }

  .quantity_container {
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    padding: 15px 20px;
    background-color: var(--el-fill-color-lighter);
    margin-top: auto;

    .quantity_item {
      display: flex;
      flex: 1;
      flex-direction: column;
      align-items: center;
      position: relative;
      padding: 0 10px;

      .number {
        font-size: 1.2rem;
        font-weight: 600;
        margin-bottom: bottom;
        color: var(--el-text-color-primary);
      }

      .label {
        font-size: 0.9rem;
        white-space: nowrap;
        color: var(--el-text-color-secondary);
      }
    }

    .divider {
      margin: 0 5px;
      width: 1px;
      height: 25px;
      background-color: linear-gradient(to bottom,
          transparent,
          var(--el-border-color) 20%,
          var(--el-border-color) 80%,
          transparent);
      align-self: center;
    }
  }

  .icon_container {
    gap: 15px;
    padding: 15px 0;
    display: flex;
    justify-content: center;
    align-items: center;

    // 图标包装器
    .icon_wrapper {
      padding: 8px;
      width: 40px;
      border-radius: 50%;
      background-color: var(--el-fill-color-lighter);
      transition: all 0.3s ease;

      &:hover {
        background-color: var(--el-fill-color);
        transform: translateY(-2px);

        :deep(svg) {
          fill: skyblue;
          transform: scale(1.08);
        }
      }
    }
  }
}

@media screen and (max-width:768px) {
  .info_container {
    width: 100%;
    margin: 10px 0;
  }
}
</style>