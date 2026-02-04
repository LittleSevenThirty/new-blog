<script setup lang="ts">
import Card from './Card.vue';
import {getRandomArticle, getRelativeArticle} from "../apis/article/index.ts";
import { ref } from 'vue';

const props = defineProps({
  title:{
    type:String,
    default:"随机文章"
  },
  prefixIcon:{
    type:String,
    default:"random_essay"
  },
  articleId:{
    type:String,
    default:''
  },
  categoryId:{
    type:String,
    default:''
  }
});

const articles=ref([{
  id:'',
  articleTitle:'',
  articleCover:'',
  createTime:''
}])

const imgRefresh=ref(false);

// 加载内容
function loadContent(){
  if(props.title=="随机文章"){
    getRandomArticleList();
  }
  else if(props.title=="相关推荐"){
    relativeArticle(props.articleId,props.categoryId);
  }
}

// 相关推荐
function relativeArticle(articleId:String,categoryId:String){
  getRelativeArticle(categoryId,articleId).then((res:any)=>{
    articles.value=formatData(res.data);
  });
}

// 获取随机文章
function getRandomArticleList(){
  getRandomArticle().then((res)=>{
    articles.value=formatData(res.data);
  })
}

// 信息标准化
function formatData(data:[]){
  data.forEach((item:any)=>{
    item.createTime=item.createTime.split(' ')[0];
  });
  return data;
}

// 触发函数
function invokeFN(){
  imgRefresh.value=true;
  getRandomArticleList();
}
</script>

<!-- 随机文章组件 -->
<srcrip setup lang="ts"></srcrip>

<template>
  <Card :title="title" :prefix-icon="prefixIcon" :suffix-icon="title=='相关推荐'?'':'rotate'" is-rotate is-scale
   @invoke="invokeFN()" v-view-request="{callback:loadContent}">

  </Card>
</template>

<style scoped lang="scss"></style>