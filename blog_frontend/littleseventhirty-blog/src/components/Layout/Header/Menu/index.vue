<script setup lang="ts">
import { ref } from 'vue';
import {Close} from '@element-plus/icons-vue'
import useWebsiteStore from '../../../../pinia/store/modules/website';

const websiteStore=useWebsiteStore();

const dialogVisible=ref(false);

const isMenuVisible=ref(true);

const isTransParent=ref(true);
</script>

<template>
  <div class="seacher_dialog_container">
    <el-dialog v-model="dialogVisible" :show-close="false" :close-on-click-model="false" :lock-scroll="true"
      :close-on-press-escape="false">
      <template v-slot:header>
        <div style="display: flex;align-items: center;justify-content:space-between;">
          <span style="font-size:1.2rem;margin-left:1rem">搜索</span>
          <el-button :icon="Close" style="margin-right:1rem;background:none;font-size:1.5rem;width:30px;border:none;"
            @click="dialogVisible = false">
          </el-button>
        </div>
      </template>
      <Search v-on:isShowSearch="dialogVisible=false" />
    </el-dialog>
  </div>
  <nav v-bind:class="{'hidden':isMenuVisible,'transparent':isTransParent}">
    <div id="menu_left">
      <div id="menus">
        <span id="blog_info">
          <a href="/">{{websiteStore.webInfo?.websiteName}}</a>
        </span>
        <div id="menu_items">

        </div>
      </div>
    </div>
    <div id="menu_right"></div>
  </nav>
</template>

<style lang="scss" scoped>
.seacher_dialog_container {

  // 影响子组件的展示效果
  :deep(.el-dialog) {
    overflow: auto;
    border-radius: 10rex;
    height: 70%;
  }

  // 扩展不同媒体展示效果
  @media screen and (max-width: 650px) {
    :deep(.el-dialog) {
      overflow: auto;
      margin-top: 0;
      margin-bottom: 0;
      border-radius: 0;
      height: 100%;
      width: 100vw;
    }
  }
}

:deep(.el-dialog) {
  transition: all 0.3s;

  @media screen and (max-width: 1400px) {
    width: 45%;
  }

  @media screen and (max-width: 1000px) {
    width: 60%;
  }

  @media screen and (max-width: 760px) {
    width: 70%;
  }

  @media screen and (max-width: 600px) {
    width: 90%;
  }
}

nav{
  position: fixed;  // 固定布局
  top:0;  // 离顶部距离
  display:flex; // 弹性布局
  height: 50px; // 盒子高度
  width:100%; // 盒子宽度
  z-index:999;  // 盒子z轴层次
  background-color: rgba(255,255,255,0.4);  // 背景色
  backdrop-filter: blur(6px); // 背景模糊度blur
  transition: top 0.3s ease-in-out, background-color 0.3s ease-in-out;  // 过渡属性

  &.hidden {
    top: -50px;
  }

  &.transparent{
    background-color: transparent;
  }

  #menu_left {
    flex:2;

    #menus {
      display: flex;   // 弹性布局
      justify-content:lfet;   // 弹性布局下，子项在盒子的位置水平集中在左侧
      align-items:center;   // 弹性布局下，子项在盒子的位置垂直集中在中间
      width: 100%;
      height: 100%;
      font-weight: bold;  // 字体厚度

      #blog_info{
        width:120px;
        margin:0 10px;  // 外边距上下0，左右10
      }

      #menu_items{
        width: 100%;
        height: 100%;
        display: flex;
        justify-content: left;

        #menu_item{
          height: 100%;
          width: 100px;
          position: relative;
          display: flex;
          justify-content: space-evenly;
        }
      }
    }
  }
}
</style>