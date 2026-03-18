<script setup lang="ts">
import Card from './Card.vue';
import { getRandomArticle, getRelativeArticle } from "../apis/article/index.ts";
import { ref, watch } from 'vue';
import router from "../router/index.ts"

const props = defineProps({
  title: {
    type: String,
    default: "随机文章"
  },
  prefixIcon: {
    type: String,
    default: "random_essay"
  },
  articleId: {
    type: String,
    default: ''
  },
  categoryId: {
    type: String,
    default: ''
  }
});


const articles = ref([{
  articleId: '',
  articleTitle: '',
  articleCover: '',
  createTime: ''
}])

const imgRefresh = ref(false);

// 监听文章传入参数的id
watch(() => props.articleId, () => {
  relativeArticle(props.articleId, props.categoryId);
});

// 加载内容
function loadContent() {
  if (props.title == "随机文章") {
    getRandomArticleList();
  }
  else if (props.title == "相关推荐") {
    relativeArticle(props.articleId, props.categoryId);
  }
}

// 相关推荐
function relativeArticle(articleId: String, categoryId: String) {
  getRelativeArticle(categoryId, articleId).then((res: any) => {
    articles.value = formatData(res.data);
  });
}

// 获取随机文章
function getRandomArticleList() {
  getRandomArticle().then((res) => {
    // console.log(res);
    articles.value = formatData(res.data);
    console.log(articles.value);
  })
}

// 信息标准化
function formatData(data: []) {
  data.forEach((item: any) => {
    // console.log(item.createTime);
    item.createTime = item.createTime.split(' ')[0];
  });
  return data;
}

// 触发函数
function invokeFN() {
  imgRefresh.value = true;
  getRandomArticleList();
  console.log(articles);
}
</script>

<!-- 随机文章组件 -->
<srcrip setup lang="ts"></srcrip>

<template>
  <Card :title="title" :prefix-icon="prefixIcon" :suffix-icon="title == '相关推荐' ? '' : 'rotate'" is-rotate is-scale
    @invoke="invokeFN()" v-view-request="{ callback: loadContent }">
    <template v-slot:content>
      <template v-for="article in articles">
        <div class="random_container" v-on:click="router.push('/article/' + article.articleId)">
          <div class="img_container">
            <img v-if="article.articleCover" alt="文章封面" :data-src="imgRefresh ? article.articleCover : ''"
              v-lazy="true">
          </div>
          <div class="text_container" :key="article.articleId">
            <div>{{ article.articleTitle }}</div>
            <div>{{ article.createTime }}</div>
          </div>
        </div>
      </template>
    </template>
  </Card>
</template>

<style scoped lang="scss">
@use "../styles/variable.scss" as *;

.random_container {
  display: flex;
  align-items: center;
  margin: 10px 0;

  .img_container {
    width: 45%;
    height: 70px;
    overflow: hidden;
    cursor: pointer;
    border-radius: 0.5em;

    img {
      width: 100%;
      height: 100%;
      transition: transform 0.3s linear;
      object-fit: cover;

      &:hover {
        transform: scale(1.1);
      }
    }
  }

  .text_container {
    width: 55%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    line-height: 30px;

    &:first-child {
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
      margin-left: 1rem;
      font-size: 1em;
      color: $menuActiveText;
    }

    &:last-child {
      font-size: 0.8em;
      margin-left: 1rem
    }
  }
}
</style>