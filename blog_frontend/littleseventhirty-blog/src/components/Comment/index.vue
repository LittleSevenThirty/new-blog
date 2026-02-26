<script lang="ts" setup>
import { ref } from "vue";
import SvgIcon from "../SvgIcon/index.vue";

const props = defineProps({
  authorId: {
    type: Number,
    required: true
  },
  typeId: {
    type: Number,
    required: true
  },
  // 是否显示头部添加框
  isShowHeader: {
    type: Boolean,
    default: true
  },
  // 评论类型
  type: Number,
  // 点赞类型
  likeType: Number
});

// 获取选项卡div
const comments = ref()
const commentsTotal = ref(0)
</script>

<template>
  <div class="comments">
    <div v-if="isShowHeader">
      <div class="comments_title">
        <svg-icon name="comment" width="1.5em" height="1.5em" />
        <span>评论({{ commentsTotal }})</span>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@use "./index.scss";
</style>