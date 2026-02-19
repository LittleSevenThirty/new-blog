<script setup lang="ts">
import { ElMessage } from 'element-plus';
import { ref } from 'vue';
import { addTreeHole } from '../../../apis/treeHole/type';
const content = ref('');
const isShowSubmit = ref(false);

function addTreeHoleBtn() {
  if (content.value == '') {
    ElMessage.warning("请输入内容");
    return;
  }
  addTreeHole(content.value).then(res => {

  });
}
</script>

<template>
  <div class="container">
    <div class="content_container">
      <div>树洞</div>
      <div>
        <input v-model="content" v-on:focus="isShowSubmit = true" type="text" placeholder="留下你的足迹吧...">
        <button v-show="isShowSubmit" v-on:click="addTreeHoleBtn()">
          <span></span>
        </button>
      </div>
    </div>
  </div>
</template>

<style lang="scss">
.container {
  background-image: url("https: //photo.16pic.com/00/61/01/16pic_6101359_b.jpg");
  background-size: cover;
  background-position: center;
  min-width: 100vw;
  height: 100vh;

  .content_container {
    position: absolute;
    top: 40%;
    left: 50%;
    z-index: 2;
    transform: translate(-50%, 50%);
  }
}
</style>