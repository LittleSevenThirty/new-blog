<!-- 展示个人div能力组件 -->
<script setup lang="ts">
import Typed from 'typed.js';
import { getSoup } from '../../../apis/thirdPartyAPIs/index.ts';
import GlitchText from '../../../components/GlitchText.vue';
import Wave from '../../../components/Wave.vue';
import useWebsiteInfoStore from '../../../pinia/store/modules/website.ts';
import { onMounted, onUnmounted, ref } from 'vue';
const websiteInfoStore = useWebsiteInfoStore();
const typed = ref<HTMLElement>();
let typedInstance: Typed;
const scrollDown = () => {
  window.scrollTo({
    behavior: 'smooth',
    top: document.documentElement.clientHeight
  })
}

onMounted(async () => {
  const res: any = await getSoup();
  // console.log("一言:");
  // console.log(res);
  // const yiYanString=res?.hitokoto;
  typedInstance = new Typed(typed.value, {
    strings: [res?.hitokoto],
    typeSpeed: 150,        // 打字速度（毫秒/字符）
    backSpeed: 75,        // 删除速度
    startDelay: 500,      // 开始前延迟
    backDelay: 3000,      // 打完后停顿多久再删除
    loop: true,           // 是否循环
    showCursor: false,     // 显示光标
  });
})

onUnmounted(() => {
  if (typedInstance) {
    typedInstance.destroy();
  }
})
</script>

<template>
  <div class="brand_container">
    <div class="brand">
      <!-- 标题 -->
      <GlitchText :text="websiteInfoStore?.webInfo?.websiteName || ''" />
      <div class="brand_text_container">
        <div class="title">
          <span ref="typed"></span>
          <span class="easy_typed_cursor">|</span>
        </div>
      </div>
    </div>
    <!-- 向下按钮 -->
    <div class="button_container" v-on:click="scrollDown">
      <svg t="1769093840622" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
        p-id="4808" width="20" height="20">
        <path
          d="M878.592 250.88q29.696 0 48.128 11.264t24.576 29.696 0 41.472-26.624 45.568q-82.944 92.16-159.744 180.224t-148.48 164.864q-19.456 20.48-45.568 31.744t-53.76 11.776-53.248-8.704-43.008-28.672q-39.936-44.032-82.944-90.112l-88.064-92.16q-43.008-46.08-85.504-90.624t-79.36-86.528q-17.408-19.456-22.528-40.448t1.024-38.4 23.552-28.672 45.056-11.264q35.84 0 98.816-0.512t137.728-0.512l153.6 0 150.528 0 125.952 0 79.872 0z"
          p-id="4809"></path>
      </svg>
      <div class="button_ripple"></div>
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

  .button_container {
    bottom: 5px;
    position: absolute;
    bottom: 15vh;
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 0;
    cursor: pointer;

    &:hover {
      animation: arow_shake 1.5s ease-out infinite;
    }

    .button_ripple {
      background: linear-gradient(to right, #f79533, #f37055, #ef4e7b, #a166ab, #5073b8, #1098ad, #07b39b, #6fba82);
      background-size: 300% 300%;
      position: absolute;
      width: 40px;
      height: 40px;
      border-radius: 50%;
      animation: gradientAnimation 4s ease-out infinite, ripple 2s ease-out infinite;
      opacity: 0.8;
      z-index: 8;
    }
  }
}

// 左右横移
@keyframes gradientAnimation {
  0% {
    background-position: 0% 50%;
  }

  25% {
    background-position: 50% 50%;
  }

  50% {
    background-position: 100% 50%;
  }

  75% {
    background-position: 50% 50%;
  }

  100% {
    background-position: 0% 50%;
  }
}

// 波纹
@keyframes ripple {
  0% {
    transform: scale(0.8);
    opacity: 0.6;
  }

  50% {
    transform: scale(1.2);
    opacity: 0.4;
  }

  100% {
    transform: scale(0.8);
    opacity: 0.6;
  }
}

// 箭头抖动
@keyframes arow_shake {
  0% {
    opacity: 1;
    transform: translateY(0px);
  }

  45% {
    opacity: 0.5;
    transform: translateY(10px);
  }

  100% {
    opacity: 1;
    transform: translateY(0px);
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