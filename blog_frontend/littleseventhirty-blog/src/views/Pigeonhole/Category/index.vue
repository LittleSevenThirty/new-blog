<script lang="ts" setup>
import Menu from "../../../components/Layout/Menu/index.vue";
import Banner from "../../../components/Banner.vue";
import router from "../../../router/index.ts";
import ArticleList from "../components/ArticleList.vue";
import { onMounted, ref } from "vue";
import { getArticleListByType } from "../../../apis/article/index.ts";
import { dayjs } from "element-plus";
import { categoryList } from "../../../apis/category/index.ts";

const categories = ref<Array<{
  categoryId: number,
  categoryName: string,
  articleCount: number,
  isActive: boolean
}>>([]);
const articleList = ref([]);
const isQueryArticle = ref(false);
// 分类标题
const title = ref('');

function getArticle(articleId: string) {
  getArticleListByType(1, articleId).then((res: any) => {
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
  categoryList().then()
})
</script>

<template>
  <div>
    <Menu only-father-container>
      <template v-slot:banner>
        <Banner title="分类" subtitle="category" />
      </template>
      <template v-slot:content>
        <template v-if="isQueryArticle">
          <div class="category_container">
            <div class="title">{{ title }}</div>
            <div id="category_items">
              <el-scrollbar>
                <div class="scroll_flex_content">
                  <template v-for="category in categories" :key="category.categoryId">
                    <p :class="{ active: category.isActive }" @click="router.push(`/category/${category.categoryId}`)"
                      class="scrollbar_item">
                      {{ category.categoryName }}
                    </p>
                  </template>
                </div>
              </el-scrollbar>
            </div>
            <el-divider />
            <ArticleList :articleList="articleList" />
          </div>
        </template>
        <template v-else>
          <div class="category_container">
            <div class="title">文章分类</div>
            <div class="item_container">
              <template v-for="category in categories" :key="category.categoryId">
                <div v-slide-in class="item" @click="router.push(`/category/${category.categoryId}`)">
                  <div>{{ category.categoryName }}</div>
                  <div>
                    <span>{{ category.articleCount }}</span>
                  </div>
                </div>
              </template>
            </div>
          </div>
        </template>
      </template>
    </Menu>
  </div>
</template>

<style lang="scss" scoped>
@use "../../../styles/variable.scss" as *;

.category_container {
  display: flex;
  flex-direction: column;
  width: 100%;

  .title {
    font-size: 1.72rem;
    padding: 1rem;
  }

  .scroll_flex_content {
    display: flex;
    white-space: nowrap;

    // 子项激活
    .active {
      color: grey !important;
      background: var(--el-color-danger-light-7) !important;
    }

    .scrollbar_item {
      display: flex;
      flex-shrink: 0;
      align-items: center;
      justify-content: center;
      height: 3em;
      margin: 0 1em;
      padding: 0.5rem 1rem;
      text-align: center;
      border-radius: 0.6em;
      border: 1px solid var(--el-color-danger-light-7);
      background: var(--el-color-danger-light-9);
      color: var(--el-color-danger);

      &:hover {
        cursor: pointer;
        color: grey;
        background: var(--el-color-danger-light-7);
      }
    }
  }

  .item_container {
    display: flex;
    flex-wrap: wrap;

    .item {
      display: flex;
      flex-direction: column;
      background-color: var(--mao-bg-category);
      opacity: 0.8;
      margin: 1em;
      border-radius: $border-radius;
      padding: 1.3rem;
      transition: all 0.3s;
    }

    &:hover {
      opacity: 1;
      box-shadow: 0 0 0.5rem 0.1rem #ccc;
      cursor: pointer;
    }

    & div:first-child {
      font-size: 1.33rem;
      font-weight: bold;
      // 左边框
      border-left: 0.15rem solid black;
      // 左边框距离
      padding-left: 1rem;
      position: relative;

      &::after {
        content: '';
        position: absolute;
        left: 0;
        bottom: -1rem;
        width: 0%;
        height: 0.2em;
        border-radius: 0.1em;
        // 蓝紫色渐变色背景
        background-color: white;
        transition: width 0.8s ease;
      }

      &:hover::after {
        width: 100%;
      }
    }

    & div:last-child {
      margin-top: 1.5rem;
      font-size: 1.3rem;
      color: grey;
    }
  }
}
</style>