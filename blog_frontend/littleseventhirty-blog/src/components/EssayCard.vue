<script lang="ts" setup>
import { ref } from 'vue';
import router from '../router';
const articleList=ref<any[]>([]);

function loadContent(){

}
</script>

<template>
  <!-- 文章列表卡片 -->
  <div v-view-request="{callback:loadContent}">
    <div v-if="articleList.length>0">
      <template v-for="(article,index) in articleList" :key="article.articleId">
        <div @click="router.push(`/article/${article.articleId}`)">
          
        </div>
      </template>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>