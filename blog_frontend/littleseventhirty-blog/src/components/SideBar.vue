<!-- 侧边栏组件 -->
<script lang="ts" setup>
import InfoCard from './InfoCard.vue';
import Card from './Card.vue';
import ElectronicClocks from './ElectronicClocks.vue';
import RandomArticle from './RandomArticle.vue';
import TagListCard from './TagListCard.vue';
import { ElMessageBox } from 'element-plus';
import useWebsiteStore from '../pinia/store/modules/website';
import { getSoup } from '../apis/thirdPartyAPIs';
import { onMounted, ref, watch } from 'vue';

const websiteStore = useWebsiteStore();
const soup = ref('');
const operationDays = ref(0);


function announcement() {
  ElMessageBox.alert(`<pre>${websiteStore.webInfo?.sidebarAnnouncement}</pre>`, "公告", {
    confirmButtonText: "关闭", // 关闭按钮自定义文本
    closeOnPressEscape: true, // 是否允许通过esc键关闭
    dangerouslyUseHTMLString: true, // 允许将文本作为HTML元素处理
  })
}

function soupSub() {
  getSoup()?.then((res: any) => {
    soup.value = res.hitokoto;
  })
}

// 计算运行天数
function getOperateDays() {
  let time = websiteStore.webInfo?.startTime;
  if (!time) return;
  // 计算运行时长
  // 假设 startTime 是一个表示开始时间的 Date 对象
  let startTime = new Date(time);
  // 获取当前时间
  let currentTime = new Date();
  // 计算两个日期之间的差值（以毫秒为单位）
  let diffInMs = currentTime.getTime() - startTime.getTime();
  // 向下取整获取运行天数
  operationDays.value = Math.floor(diffInMs / (1000 * 60 * 60 * 24));
}

// 监听数据是否成功得到
watch(() => websiteStore.webInfo, () => {
  if (websiteStore.webInfo?.startTime) {
    getOperateDays()
  }
});

onMounted(() => {
  soupSub();
  getOperateDays();
})
</script>

<template>
  <div>
    <div>
      <InfoCard />
    </div>
    <div class="anouncement_container">
      <!-- 二次元少女 -->
      <div class="anime_girl">
        <img src="../assets/images/动漫少女坐姿-公告_压缩.png" alt="二次元少女">
      </div>
      <Card title="公告" prefix-icon="announcement" suffix-icon="jt_y" isDithering isArrow v-on:invoke="announcement()">
        <template v-slot:content>
          <pre class="pre_text">{{ websiteStore.webInfo?.sidebarAnnouncement }}</pre>
        </template>
      </Card>
    </div>
    <!-- 时钟 -->
    <div id="electronicClock">
      <ElectronicClocks />
    </div>
    <!-- 随机文章 -->
    <div id="randomArticle">
      <RandomArticle />
    </div>
    <!-- 标签列表卡片 -->
    <div id="tagListCard">
      <TagListCard />
    </div>
    <!-- 杂项卡片 -->
    <!-- 后期如果有愿意给钱的可以放到这里 -->
    <div id="soup">
      <Card title="鸡汤来喽😄" prefix-icon="edit" suffix-icon="rotate" is-rotate is-scale @invoke="soupSub">
        <template v-slot:content>
          <div class="soup_container">
            <i class="soup_quote_left">"</i>
            <Transition name="fade" mode="out-in">
              <p class="soup_text" :key="soup">{{ soup }}</p>
            </Transition>
            <i class="soup_quote_right">"</i>
          </div>
        </template>
      </Card>
    </div>
    <div id="consult">
      <Card title="网站咨询" prefix-icon="statistics" is-scale>
        <template v-slot:content>
          <div class="statistics">
            <div>文章数目：<span>{{ websiteStore.webInfo?.articleCount }}</span></div>
            <div>运行时长：<span>{{ operationDays }}</span></div>
            <div>全站字数：<span>{{ websiteStore.webInfo?.wordCount }}</span></div>
            <div>访问次数：<span>{{ websiteStore.webInfo?.visitedCount }}</span></div>
            <div>最后更新：<span>{{ websiteStore.webInfo?.lastUpdatetime }}</span></div>
          </div>
        </template>
      </Card>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@use "../styles/variable.scss" as *;

// 二次元少女，或者说live-2D
.anouncement_container {
  position: relative;
  margin-top: 70px;
}

.pre_text {
  text-align: left;
  overflow: auto; // 超出内容显示滚动条
  white-space: pre-wrap; // 允许换行
  word-wrap: break-word; // 超长单词强制换行
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

.soup_container {
  position: relative;
  padding: 15px 25px;
  margin: 10px 0;

  .soup_quote_left,
  .soup_quote_right {
    position: absolute;
    font-size: 32px;
    color: var(--el-color-primary-light-7);
    font-family: "Times New Roman", serif;
    opacity: 0.6;
  }

  .soup_quote_left {
    top: -5px;
    left: 5px;
  }

  .soup_quote_right {
    bottom: -15px;
    right: 5px;
  }

  // 鸡汤字体
  .soup_text {
    font-size: 15px;
    line-height: 1.6;
    color: var(--el-text-color-primary);
    text-align: center;
    font-style: italic;
    margin: 0;
    padding: 0 10px;
    transition: all 0.3s ease;
  }
}

.statistics {
  display: flex;
  flex-direction: column;
  color: $menuActiveText;

  div {
    margin: 5px 20px;
    font-size: 14px;
    display: flex;
    justify-content: space-between;
  }
}

// 添加过渡动画样式
.fade-enter-active,
.fade-leave-active {
  transition: all 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
  transform: translateY(10px);
}

.fade-enter-to,
.fade-leave-from {
  opacity: 1;
  transform: translateY(0);
}
</style>