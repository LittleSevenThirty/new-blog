<!-- 展示个人div能力组件 -->
<script setup lang="ts">
import GlitchText from './GlitchText.vue';
import useWebstiInfoStore from '../pinia/store/modules/website.ts';
import { reactive } from 'vue';
const websiteInfoStore = useWebstiInfoStore();
const obj = reactive({
  output: "你好",
});
</script>

<template>
  <div class="brand_container">
    <div class="brand">
      <!-- 标题 -->
      <GlitchText :text="websiteInfoStore?.webInfo?.websiteName || ''" />
      <div class="brand_text_container">
        <div class="title">
          {{ obj.output }}
          <span class="easy_typed_cursor">|</span>
        </div>
      </div>
    </div>
    
  </div>
</template>

<style scoped lang="scss">
.brand_container {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  width: 100%;
  height: 100vh;
  position: relative;
  min-height: 10rem;
  color: var(--header-text-color);

  .brand {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    position: fixed;
    z-index: -1;
    top: 15rem;

    // 文本容器
    .brand_text_container {
      // 白色半透明背景版
      background-color: rgba(255, 255, 255, 0.5);
      padding: .5rem;
      border-radius: 0.5rem;

      // 文本
      .title {
        letter-spacing: 0.1em; // 字体间距
        background: linear-gradient(to right, #f79533, #f37055, #ef4e7b, #a166ab, #5073b8, #1098ad, #07b39b, #6fba82);
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
        font-weight: 700;
        font-size: 1.5rem;

        .easy_typed_cursor {
          opacity: 0;
          -webkit-animation: blink 0.7s infinite;
          -moz-animation: blink 0.7s infinite;
          animation: blink 1s infinite;
          background: linear-gradient(90deg, #f79533, #f37055, #ef4e7b, #a166ab, #5073b8, #1098ad, #07b39b, #6fba82);
          -webkit-background-clip: text;
          -webkit-text-fill-color: transparent;
        }
      }
    }
  }
}

// 闪烁
@keyframes blink {
  0% {
    opacity: 0;
  }

  100% {
    opacity: 1;
  }
}

@media screen and (max-width:500px) {
  .title {
    font-size: 1em;
  }

  .easy_typed_cursor {
    font-size: 1em;
  }
}
</style>