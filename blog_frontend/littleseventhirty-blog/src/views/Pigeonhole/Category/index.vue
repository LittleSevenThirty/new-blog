<script lang="ts" setup>
import Menu from "../../../components/Layout/Menu/index.vue";
import Banner from "../../../components/Banner.vue";
import router from "../../../router/index.ts";
import ArticleList from "../components/ArticleList.vue";
import { ref } from "vue";

const categories = ref<Array<{
  categoryId: number,
  categoryName: string,
  isActive: boolean
}>>([]);
const articleList = ref([]);
const isQueryArticle = ref(false);
// 分类标题
const title = ref('');
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
            <div class="title">
              {{ title }}
            </div>
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
        <template v-else></template>
      </template>
    </Menu>
  </div>
</template>

<style lang="scss" scoped>
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
}
</style>