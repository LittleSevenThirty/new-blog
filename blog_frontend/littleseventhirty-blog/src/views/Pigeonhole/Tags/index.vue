<script lang="ts" setup>
import Menu from "../../../components/Layout/Menu/index.vue";
import Banner from "../../../components/Banner.vue";
import { onMounted, ref, watch } from "vue";
import { useRoute } from "vue-router";
import router from "../../../router/index.ts";
import { getTagList } from "../../../apis/tag/index.ts";
import { getArticleListByType } from "../../../apis/article/index.ts";
import { dayjs } from "element-plus";
import ArticleList from "../components/ArticleList.vue";

const route = useRoute();

const isQueryArticle = ref(false);  // false时只是简略信息，true时改变格式查看具体信息
const tags = ref<Array<{
  tagId: number,
  tagName: string,
  articleCount: number
}>>([]);
const articleList = ref([]);
const title = ref('');


function getArticle(articleId: string) {
  getArticleListByType(2, articleId).then((res: any) => {
    if (res.code == 200 && res.data !== undefined) {
      res.data.forEach((item: any) => {
        item.createTime = dayjs(item.createTime).format("YYYY-MM-DD");
      });
      articleList.value = res.data;
    } else {
      articleList.value = [];
    }
  })
}

onMounted(() => {
  getTagList().then((res: any) => {
    if (res.code == 200) {
      tags.value = res.data;
    }
  });
  if (route.params.id) {
    isQueryArticle.value = true;
    tags.value.forEach((item) => {
      if (item.tagId == Number(route.params.id)) {
        title.value = item.tagName;
      }
    });
    getArticle(route.params.id);
  }
});

watch(() => route.params.id, (id) => {
  if (id) {
    isQueryArticle.value = true;
    tags.value.forEach((item) => {
      if (item.tagId == Number(id)) {
        title.value = item.tagName;
      }
    });
    getArticle(id);
  } else {
    isQueryArticle.value = false;
  }
})
</script>

<template>
  <Menu only-father-container>
    <template v-slot:banner>
      <Banner title="标签页" subtitle="tags" />
    </template>
    <template v-slot:content>
      <div class="tags_container">
        <div class="title" v-if="!isQueryArticle">
          标签 {{ title }}
        </div>
        <div class="title" v-else>
          标签 - {{ title }}
        </div>
        <template v-if="!isQueryArticle">
          <div class="item_container">
            <template v-for="tag in tags" :key="tag.tagId">
              <div v-slide-in class="item" v-on:click="router.push(`/tags/${tag.tagId}`)">
                <span @click="router.push(`/tags/${tag.tagId}`)"># {{ tag.tagName }}</span>
                <span>{{ tag.articleCount }}</span>
              </div>
            </template>
          </div>
        </template>
        <template v-else>
          <el-divider />
          <ArticleList :article-list="articleList" />
        </template>
      </div>
    </template>
  </Menu>
</template>

<style lang="scss">
.tags_container {
  display: flex;
  flex-direction: column;
  width: 100%;

  .title {
    font-size: 2rem;
    padding: 1rem 0 0 1rem;
  }

  .item_container {
    display: flex;
    flex-wrap: wrap;
    padding: 1rem;

    .item {
      display: flex;
      font-size: 1.2rem;
      margin: 0.5rem;
      padding: 0.5rem;
      border: 1px solid var(--el-border-color);
      border-radius: 5px;
      cursor: pointer;
      transition: all 0.3s;
      color: #565352;

      & span:first-child {
        color: grey;
        margin-right: 0.4rem;
      }

      & span:last-child {
        display: flex;
        align-items: center;
        justify-content: center;
        color: white;
        margin-left: 0.4rem;
        font-size: 0.6rem;
        background-color: #555555;
        padding: 0.1rem 0.5rem;
        border-radius: 0.7rem;
      }

      &:hover {
        border: 1px solid skyblue;
        transform: translateY(-2rem);
      }
    }
  }
}
</style>