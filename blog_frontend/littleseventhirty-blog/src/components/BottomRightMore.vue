<!-- 更多功能公用组件 -->
<script setup></script>

<template>
  <!-- tailwindcss maring-y:4 -->
  <div>
    <el-tooltip content="更多功能" placement="right" effect="light">
      <div class="back_to_top more_rotating">
        <svg t="1768897703587" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
          p-id="6718" width="30" height="30">
          <path d="M256 512m-74.666667 0a74.666667 74.666667 0 1 0 149.333334 0 74.666667 74.666667 0 1 0-149.333334 0Z"
            fill="#111111" p-id="6719"></path>
          <path d="M512 512m-74.666667 0a74.666667 74.666667 0 1 0 149.333334 0 74.666667 74.666667 0 1 0-149.333334 0Z"
            fill="#111111" p-id="6720"></path>
          <path d="M768 512m-74.666667 0a74.666667 74.666667 0 1 0 149.333334 0 74.666667 74.666667 0 1 0-149.333334 0Z"
            fill="#111111" p-id="6721"></path>
        </svg>
      </div>
    </el-tooltip>
  </div>
</template>

<style lang="scss" scoped>
.back_to_top {
  height: 30px !important;
  width: 30px !important;

  @media screen and (max-width:768px) {
    height: 30px !important;
    width: 30px !important;
  }
}

.more_rotating {
  animation: rotating 3s linear infinite;
}
</style>