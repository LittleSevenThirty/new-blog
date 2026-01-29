<script lang="ts" setup>
import { ref, watch } from 'vue';
import router from '../router';
import { getArticleList } from '../apis/article';
import usePaginationStore from '../pinia/store/modules/pagination';
import { ElMessage } from 'element-plus';
import { useWindowSize } from '@vueuse/core';
const articleList = ref<any[]>([]);
const paginationStore = usePaginationStore();

// 响应式窗口的宽度
const { width } = useWindowSize();

// 一旦用户点击翻页就执行
watch(() => paginationStore.articlePagination.current, () => {
  loadContent();
  // 重新滚动到顶部
  window.scrollTo(0, 0);
})

function loadContent() {
  getArticleList(paginationStore.articlePagination.current, paginationStore.articlePagination.pageSize).then((res: any) => {
    if (res.code == 200) {
      paginationStore.articlePagination.total = res.data.total;
      // 过滤内容
      res.data.page = res.data.page.map((item: any) => {
        item.articleContent = item.articleContent.replace(/[*#>`~\-\\[\]()\s]|(\n\n)/g, '')
        // 提取前 50 个字符
        item.articleContent = item.articleContent.substring(0, 60) + '...';
        return item;
      });
      articleList.value = res.data.page;
    } else {
      ElMessage.error(res.msg);
    }
  });
}
</script>

<template>
  <!-- 文章列表卡片 -->
  <div v-view-request="{ callback: loadContent }">
    <div v-if="articleList.length > 0">
      <template v-for="(article, index) in articleList" :key="article.articleId">
        <!-- 一张卡片容器pc端内容左右布局，移动端内容上下布局 -->
        <div v-slide-in @click="router.push(`/article/${article.articleId}`)"
          class="h-92 md:h-60 mt-4 flex flex-col md:flex-row card overflow-hidden shadow-md mb-5 mx-2 dark:bg-[#1D1D1D]">
          <!-- 同时负责移动端上图下内容的显示和桌面端奇数卡的左图右内容 -->
          <div class="w-full md:h-full md:w-1/2 h-40" v-if="index % 2 == 1 || width < 768px">
            <div class="w-full h-full relative">
              <div class="relative w-full h-full img">
                <img class="w-full h-full object-cover hover:scale-110 ease-liner duration-300" v-lazy="true"
                  :data-src="article?.articleCover" alt="文章封面" />
              </div>
              <div class="text-white text-xs p-1.5 w-11 absolute top-0 left-0 rounded-br-lg classify">
                {{ article.categoryName ? article.categoryName : "分类名为空" }}
              </div>
            </div>
          </div>

        </div>
      </template>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@use "../styles/variable.scss" as *;

.card {
  border-radius: $border-radius;

  &:hover img {
    transform: scale(1.1); // 变大一点
  }

  .img {
    position: relative;
    overflow: hidden;

    img {
      object-fit: cover; // 对可替换元素<img><video>对其盒子的占据方式
      width: 100%;
      height: 100%;
      transition: transform 0.3s linear;
    }
  }
}

.classify {
  z-index: 1;
  top: 0;
  color: white;
  padding: 10px;
  backdrop-filter: blur(5px); // 模糊遮挡
}
</style>