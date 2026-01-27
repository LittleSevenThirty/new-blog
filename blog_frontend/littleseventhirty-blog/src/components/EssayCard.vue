<script lang="ts" setup>
import { ref } from 'vue';
import router from '../router';
import { getArticleList } from '../apis/article';
const articleList=ref<any[]>([]);

function loadContent(){
  getArticleList(0,0).then(res=>{

  });
}
</script>

<template>
  <!-- 文章列表卡片 -->
  <div v-view-request="{callback:loadContent}">
    <div v-if="articleList.length>0">
      <template v-for="(article,index) in articleList" :key="article.articleId">
        <div v-slide-in @click="router.push(`/article/${article.articleId}`)" class="h-92 md:h-60 mt-4 flex flex-col md:flex-row card overflow-hidden shadow-md mb-5 mx-2 dark:bg-[#1D1D1D]">
          
        </div>
      </template>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>