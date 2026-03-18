<script setup lang="ts">
import { ElMessage } from 'element-plus';
import { onMounted, ref } from 'vue';
import { addTreeHole, getTreeHoleList } from '../../../apis/treeHole/type';
import Danmaku from 'vue-danmaku';

const content = ref('');
const isShowSubmit = ref(false);
const treeHoleList = ref<Array<{
  nickName: string,
  avatar: string,
  content: string
}>>([]);

function addTreeHoleBtn() {
  if (content.value == '') {
    ElMessage.warning("请输入内容");
    return;
  }
  addTreeHole(content.value).then((res: any) => {
    if (res.code == 200) {
      ElMessage.success("添加成功");
      getTreeHole();
      content.value = '';
    } else {
      ElMessage.error("添加失败：" + res.msg);
    }
  });
}

function getTreeHole() {
  getTreeHoleList().then((res: any) => {
    if (res.code == 200) {
      treeHoleList.value = res.data;
    }
  })
}

onMounted(() => {
  getTreeHole();
})
</script>

<template>
  <div class="container">
    <div class="content_container">
      <div>树洞</div>
      <div>
        <input v-model="content" v-on:focus="isShowSubmit = true" type="text" placeholder="留下你的足迹吧...">
        <button v-show="isShowSubmit" v-on:click="addTreeHoleBtn()">
          <span style="color:grey;font-style:italic;font-weight:bold">提交</span>
        </button>
      </div>
    </div>
    <Danmaku :debounce="3000" random-channel :speeds="80" :channels="5" is-suspend v-model:danmus="treeHoleList"
      use-slot loop style="height:100vh; width:100vw;">
      <template v-slot:danmu="{ danmu }">
        <div class="barrage_container">
          <div>
            <el-avatar :src="danmu.avatar" />
          </div>
          <div><span>{{ danmu.nickname }}：</span><span>{{ danmu.content }}</span></div>
        </div>
      </template>
    </Danmaku>
  </div>
</template>

<style lang="scss">
@use "../../../styles/variable.scss" as *;

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

  // 弹幕
  .barrage_container {
    top: 50px;
    display: flex;
    align-items: center;
    position: relative;

    // 下边框动画
    &::after {
      content: '';
      position: absolute;
      left: 0;
      bottom: 0;
      width: 0;
      height: 0.2em;
      border-radius: 0.1em;
      // 蓝紫色渐变色背景
      background: linear-gradient(to right, #00c6ff, #0072ff);
      transition: width 0.3s ease;
      /* 过渡动画效果 */
    }

    &:hover::after {
      width: 100%;
    }

    & div:last-child span:first-child {
      margin-left: 0.5rem;
      color: white;
      font-weight: bold;
    }

    & div:last-child span:last-child {
      font-size: 1.2rem;
    }

    & div:last-child {
      // 悬浮动态移动下边框
      border-bottom: 1px solid #ebebeb;
      padding: 0.5rem;
      margin-left: 0.5rem;
      border-radius: $border-radius;
      background-color: rgba(255, 255, 255, 0.2);

    }
  }
}
</style>