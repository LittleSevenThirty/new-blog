<script setup>
import useWebsiteStore from "../../../pinia/store/modules/website.ts";
const websiteStore = useWebsiteStore();
</script>

<template>
  <div class="footer">
    <div class="footer_container">
      <div class="footer_text">
        <div>&copy;{{ websiteStore.webInfo?.websiteName }}</div>
        <div v-if="websiteStore.webInfo.recordInfo">备案号:<a href="https://beian.miit.gov.cn">{{
          useWebsite.webInfo?.recordInfo }}</a></div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.footer {
  height: 100px;
  width: 100%;
  box-shadow: 0 0 10px 10px rgba(0, 0, 0, 0.1); // 盒子阴影
  // 渐变底部
  background-color: var(--mao-bg-footer);

  .footer_container {
    height: 100%;
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;

    .footer_text {
      line-height: 20px;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      color: #fff;
    }
  }
}
</style>