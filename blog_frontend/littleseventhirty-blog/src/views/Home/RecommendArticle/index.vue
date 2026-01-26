<script lang="ts" setup>
import { ref } from 'vue';
import { getRecommendArticleList } from '../../../apis/article';
import { ArticleSearch } from '../../../apis/article/type';
import { Swiper, SwiperSlide } from 'swiper/vue';
import router from "../../../router/index.ts";
import { Navigation, Pagination, Autoplay } from 'swiper/modules';  // 导入导航，分页，自动播放模块
// 对应模块的对应css
import "swiper/css";
import "swiper/css/navigation";
import "swiper/css/pagination";
import "swiper/css/autoplay";

const recommendArticles = ref<any[]>([]);

const modules = ref([Navigation, Pagination, Autoplay]);

function loadContent() {
  getRecommendArticleList().then((res) => {
    // 数据处理
    res.data = res.data.map((item: ArticleSearch) => {
      // 替换特殊字符
      item.articleContent = item.articleContent.replace(/[*#>`~\-\\[\]()\s]|(\n\n)/g, "");
      // 截取前50个左右的字符
      item.articleContent = item.articleContent.substring(0, 25) + "...";
      return item;
    });
    recommendArticles.value = res.data;
  })
}
</script>

<template>
  <!-- 标题 -->
  <div>
    <el-divider direction="vertical" content-position="center">
      <div class="flex items-center">
        <el-icon>
          <svg t="1769348119734" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
            p-id="4836" width="16" height="16">
            <path
              d="M927.536 428.164l-0.016-0.02c-17.781-28.595-48.783-46.952-80.946-47.912l-217.996-1.035 2.714-7.916a375.275 375.275 0 0 0 20.734-123.142c0-21.336-1.901-42.67-5.788-65.236l-3.836-16.548c-10.554-45.732-50.667-77.673-97.586-77.673-55.266 0-100.228 44.986-100.228 100.286 0 3.148 0.118 6.262 0.405 8.823-3.004 109.933-131.346 217.866-176.497 217.866l-155.917 0.071c-17.074 0-30.951 13.897-30.951 31.011l-0.384 436.147c0 16.78 14.85 29.845 34.527 30.408l134.95-0.273 5.258 0.883 3.639-0.51 515.088 0.136c16.805-1.194 33.36-6.584 47.47-15.575 14.828-9.519 27.628-23.26 33.382-35.817l6.642-11.084c35.476-84.662 79.199-348.829 79.619-351.445l0.628-9.886c1.675-21.95-3.476-43.216-14.911-61.56z m-698.81 434.142l-96.358 0.32 0.33-395.526 96.028-0.135v395.341z m662.829-371.769l-0.243 1.604c-7.447 44.073-46.128 267.346-74.647 336.058l-0.458 0.9-3.977 6.314c-3.748 7.653-9.935 14.59-17.588 19.494-6.648 4.242-14.747 7.057-21.649 7.55L279.8 862.27V464.693l4.821-0.949c81.067-15.788 208.01-142.37 211.42-268.513l-0.374-5.922c-0.009-27.486 22.04-49.564 49.152-49.564 23.007 0 42.675 15.666 47.863 38.104l3.518 14.972c3.184 18.614 4.755 37.057 4.755 55.193 0 36.527-6.102 72.529-18.165 106.987l-26.22 75.172 278.586 0.06 7.416 0.73c16.856 0.603 32.562 9.672 41.61 24.213 5.872 9.451 8.137 20.36 7.374 35.361z"
              fill="#409EFF" p-id="4837"></path>
          </svg>
        </el-icon>
        <span class="ml-[5px]">推荐</span>
      </div>
    </el-divider>
  </div>
  <!-- 先暂停，补充自定义指令以及全局API注册全局指令知识 -->
  <!-- 使用观察器，懒加载对应资源(推荐文章) -->
  <div v-view-request="{ callback: loadContent }">
    <swiper class="h-[200px] recommend" :modules="modules" loop navigation :pagination="{ clickable: true }"
      :autoplay="{ delay: 2500 }" v-if="recommendArticles.length > 0">
      <swiper-slide v-for="recommendArticle in recommendArticles" :key="recommendArticle.articleId"
      @click="router.push(`/article/${recommendArticle.articleId}`)">
        <div class="item_text_container">
          <div style="font-size:30px">
            {{ recommendArticle?.articleTitle }}
          </div>
          <div style="font-size:15px">
            {{ recommendArticle?.createTime }}
          </div>
          <div style="font-size:18px">
            {{ recommendArticle?.articleContent }}
          </div>
        </div>
        <el-image :src="recommendArticle.articleCover" fit="fill" :lazy="true"/>
      </swiper-slide>
      <div class="swiper-pagination"></div>
    </swiper>
  </div>
  <el-skeleton v-if="recommendArticles.length==0" :rows="5" animated></el-skeleton>
</template>

<style lang="scss" scoped>
@use "../../../styles/variable.scss" as *;

.recommend {
  border-radius: $border-radius;

  .item_text_container{
    position:absolute;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items:center;
    flex-direction: column;
    // 字体设置
    color:#fff;
    font-size: 20px;
    font-weight: bold;
    background-color:rgba(0,0,0,0.1);
    padding: 0 20px;
    z-index: 1;
    line-height: 2;
  }

  @media screen and (min-width:760px){
    .el-image{
      transform: translate(0, -20%);
      transition: transform 0.5s;
    }
  }
}
</style>