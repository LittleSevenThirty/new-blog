<script lang="ts" setup>
import router from '../../../router';
import SvgIcon from "../../../components/SvgIcon/index.vue";
const props = defineProps({
  articleList: {
    type: Array<{
      articleId: number,
      articleCover: string,
      createTime: string,
      articleTitle: string,
      visitedCount: number,
      tags: Array<{
        tagId: number,
        tagName: string
      }>
    }>,
    required: true
  }
});
</script>

<template>
  <div class="category_article_container">
    <template v-for="article in articleList" :key="article.articleId">
      <div>
        <div class="article_left_container">
          <img :src="article.articleCover" alt="缩略图">
        </div>
        <div class="article_right_container">
          <span class="article_time">{{ article.createTime }}</span>
          <span class="article_title" v-on:click="router.push(`/article/${article.articleId}`)">
            <span>{{ article.articleTitle }}</span>
            <span>
              <SvgIcon name="heat" />{{ article.visitedCount }}
            </span>
          </span>
          <span class="article_tag">
            <template v-for="tag in article.tags" :key="tag.tagId">
              <span v-on:click="router.push(`/tags/${tag.tagId}`)">{{ tag.tagName }}</span>
            </template>
          </span>
        </div>
      </div>
    </template>
  </div>
</template>

<style lang="scss" scoped>
.category_article_container {
  padding: 1rem;
  display: flex;
  flex-wrap: wrap;
  width: 100%;

  div {
    margin: 0.5rem 0;
    width: 50%;
    display: flex;
    transition: all 0.3s;

    @media screen and (max-width:900px) {
      width: 100%;
    }

    .article_left_container {
      border-radius: 1em;
      width: 12.5em;
      height: 6em;
      overflow: hidden;
      border: #862e9c 2px solid;
      object-fit: cover;

      img {
        width: 100%;
        height: 100%;
        transition: transform 0.3s linear;

        &:hover {
          transform: scale(1.1);
        }
      }
    }

    .article_right_container {
      margin-left: 0.4rem;
      display: flex;
      flex-direction: column;
      justify-content: center;
    }
  }
}
</style>