<!-- 侧边栏组件 -->
<script lang="ts" setup>
import InfoCard from './InfoCard.vue';
import Card from './Card.vue';
import ElectronicClocks from './ElectronicClocks.vue';
import RandomArticle from './RandomArticle.vue';
import { ElMessageBox } from 'element-plus';
import useWebsiteStore from '../pinia/store/modules/website';
const websiteStore=useWebsiteStore();

function announcement(){
  ElMessageBox.alert(`<pre>${websiteStore.webInfo?.sidebarAnnouncement}</pre>`,"公告",{
    confirmButtonText:"关闭", // 关闭按钮自定义文本
    closeOnPressEscape: true, // 是否允许通过esc键关闭
    dangerouslyUseHTMLString: true, // 允许将文本作为HTML元素处理
  })
}
</script>

<template>
  <div>
    <div>
      <InfoCard/>
    </div>
    <div class="anouncement_container">
      <!-- 二次元少女 -->
      <div class="anime_girl">
        <img src="../assets/images/动漫少女坐姿-公告_压缩.png" alt="二次元少女" >
      </div>
      <Card title="公告" prefix-icon="announcement" suffix-icon="jt_y" isDithering isArrow
        v-on:invoke="announcement()">
        <template v-slot:content>
          <pre class="pre_text">{{ websiteStore.webInfo?.sidebarAnnouncement }}</pre>
        </template>
      </Card>
    </div>
    <!-- 时钟 -->
    <div id="electronicClock">
      <ElectronicClocks/>
    </div>
    <!-- 随机文章 -->
    <div id="randomArticle">
      <RandomArticle/>
    </div>
  </div>
</template>

<style lang="scss" scoped>
// 二次元少女，或者说live-2D
.anouncement_container{
  position:relative;
  margin-top:70px;
}

.pre_text{
  text-align: left;
  overflow:auto;  // 超出内容显示滚动条
  white-space:pre-wrap; // 允许换行
  word-wrap:break-word;   // 超长单词强制换行
  max-width: 100%;
}

.anime-girl {
  position: absolute;
  top: -85px; // 向上偏移以实现坐在容器上的效果
  left: 50%;
  transform: translateX(-50%);
  z-index: 1;
  
  img {
    max-height: 120px;
    width: auto;
    // 添加一些阴影效果增强立体感
    filter: drop-shadow(0 2px 4px rgba(0, 0, 0, 0.1));
    // 图片禁止拖拽
    -webkit-user-drag: none;
    user-drag: none;
  }
}
</style>