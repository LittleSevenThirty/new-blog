<!-- 留言详情页面 -->
<script lang="ts" setup>
import { ArrowLeftBold } from '@element-plus/icons-vue';
import router from '../../../../router';
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import { getLeaveWordList } from '../../../../apis/leaveWord';
import { MdPreview } from 'md-editor-v3';
import "md-editor-v3/lib/preview.css";
import SvgIcon from "../../../../components/SvgIcon/index.vue";
import { cancelLike, userLike } from "../../../../apis/like/index.ts";
import { cancelFavorite, userFavorite } from '../../../../apis/favorite/index.ts';
import { ElMessage } from 'element-plus';
import { useColorMode } from '@vueuse/core';
import Comment from '../../../../components/Comment/index.vue';

const mode = useColorMode();
const route = useRoute();
const leaveWord = ref<{
  leaveWordId: number,
  userId: number,
  avatar: string,
  nickname: string,
  content: string,
  commentCount: number,
  likeCount: number,
  favoriteCount: number,
  createTime: string
}>();
const like = ref(false);
const favorite = ref(false);
const loadingComment = ref(false);

function getLeaveWord() {
  getLeaveWordList(route.params.id).then((res: any) => {
    leaveWord.value = res.data[0];
    loadingComment.value = true;
  })
}

function likeFunc() {
  if (like.value) {
    cancelLikeFunc();
  } else {
    userLikeFunc();
  }
}

function favoriteFunc() {
  if (favorite.value) {
    cancelFavoriteFunc();
  } else {
    userFavoriteFunc();
  }
}

function cancelLikeFunc() {
  if (!like.value) return;
  cancelLike(3, route.params.id.toString()).then((res: any) => {
    if (res.code == 200 && leaveWord.value) {
      leaveWord.value.likeCount--;
      like.value = false;
    } else {
      like.value = true;
      ElMessage.warning(res.msg);
    }
  })
}

function userLikeFunc() {
  if (like.value) return;
  userLike(3, route.params.id.toString()).then((res: any) => {
    if (res.code == 200 && leaveWord.value) {
      leaveWord.value.likeCount++;
      like.value = true;
      ElMessage.success("点赞成功");
    } else {
      like.value = false;
      ElMessage.warning(res.msg);
    }
  });
}

function cancelFavoriteFunc() {
  if (!favorite.value) return;
  cancelFavorite(2, route.params.id.toString()).then((res: any) => {
    if (res.code == 200 && leaveWord.value) {
      favorite.value = false;
      leaveWord.value.favoriteCount--;
    } else {
      favorite.value = true;
      ElMessage.warning(res.msg);
    }
  })
}

function userFavoriteFunc() {
  if (favorite.value) return;
  userFavorite(2, route.params.id.toString()).then((res: any) => {
    if (res.code == 200 && leaveWord.value) {
      favorite.value = true;
      leaveWord.value.favoriteCount++;
      ElMessage.success("收藏成功");
    } else {
      favorite.value = false;
      ElMessage.warning(res.msg);
    }
  });
}

function isLikeFunc() { }

function isFavoriteFunc() { }

onMounted(() => {
  window.scrollTo(0, 0);
  console.log(route); // 获取一下路由信息
  getLeaveWord();
})
</script>

<template>
  <div>
    <div id="return">
      <el-link :icon="ArrowLeftBold" @click="router.push('/message')">回到留言列表</el-link>
      <el-divider />
    </div>
    <div class="user">
      <span>
        <el-avatar icon="el-icon-user-solid" shape="circle" :src="leaveWord?.avatar" fit="fill"></el-avatar>
      </span>
      <div class="detail">
        <span class="name">{{ leaveWord?.nickname }}</span>
        <span class="time">{{ leaveWord?.createTime }}</span>
      </div>
    </div>
    <div class="content">
      <MdPreview :model-value="leaveWord?.content" :theme="mode"></MdPreview>
    </div>
    <div class="container">
      <div class="count">
        <div id="comments">
          <SvgIcon name="comment"></SvgIcon>
          <span>{{ leaveWord?.commentCount }}</span>
        </div>
        <div v-on:click="likeFunc">
          <SvgIcon v-show="!like" name="like" />
          <SvgIcon v-show="like" name="like-selected" />
          <span>{{ leaveWord?.likeCount }}</span>
        </div>
        <div v-on:click="favoriteFunc">
          <SvgIcon v-show="!favorite" name="collection" />
          <SvgIcon v-show="favorite" name="collection-selected" />
          <span>{{ leaveWord?.favoriteCount }}</span>
        </div>
        <div v-on:click="favoriteFunc"></div>
      </div>
    </div>
    <el-divider />
    <!-- 用户评论 -->
    <Comment :type="2" :like-type="2" :author-id="leaveWord?.userId" :type-id="leaveWord?.leaveWordId"
      :is-show-header="true" v-if="loadingComment"></Comment>
  </div>
</template>

<style lang="scss" scoped>
.user {
  display: flex;
  justify-content: flex-start;
  align-items: center;

  .detail {
    display: flex;
    flex-direction: column;
    margin-left: 0.2rem;

    .name {
      font-size: 1em;
      margin-bottom: 0.5em;
      color: #0072ff;
    }

    .time {
      font-size: 0.75em;
      color: grey;
    }
  }
}

.content {
  margin: 1rem 0;
}

:deep(.md-editor-toolbar-left) {
  flex-wrap: wrap;
}

.container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 2rem;

  .count {
    display: flex;
    margin-top: 0.5rem;
    color: grey;

    div {
      display: flex;
      align-items: center;
      margin-right: 1rem;
      cursor: pointer;
    }

    span {
      margin-left: 0.2rem;
    }
  }
}
</style>