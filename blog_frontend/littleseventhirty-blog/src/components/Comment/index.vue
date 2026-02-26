<script lang="ts" setup>
import { h, nextTick, ref } from "vue";
import SvgIcon from "../SvgIcon/index.vue";
import { getComment, addComment } from "../../apis/comment";
import { ElMessage, ElNotification } from "element-plus";

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

// 输入框输入内容
const textarea = ref('');
// 获取选项卡div
const comments = ref()
const commentsTotal = ref(0);
// 文本框
const myInput = ref();
// 是否加载
const isLoading = ref(false);
// 添加一个 ref 来跟踪当前活动的评论框
const activeCommentId = ref(null);
// 是否预览
const isPreview = ref(false);
// 查询评论数
const pageSize = ref(2)

function getComments(typeId: number, pageNum: string, pageSize: string) {
  getComment(<number>props.type, props.typeId, pageNum, pageSize).then((res: any) => {
    if (res.code == 200) {
      isLoading.value = false;
    }
  });
}

// 修改父评论的表情选择处理
function handleEmojiSelect(emoji: string) {
  // 如果没有活动的评论框，则是父评论
  if (!activeCommentId.value) {
    let start = myInput.value.selectionStart;
    let end = myInput.value.selectionEnd;
    textarea.value = textarea.value.substring(0, start) + emoji + textarea.value.substring(end, textarea.value.length);
    nextTick(() => {
      myInput.value.focus();
      myInput.value.selectionStart = start + emoji.length;
      myInput.value.selectionEnd = start + emoji.length;
    });
  }
}

// 修改父评论的表情按钮点击处理
function handleParentEmojiButtonClick(event: Event) {
  event.stopPropagation();
  event.preventDefault();

  // 关闭所有回复框
  if (comments.value) {
    comments.value.forEach((comment: any) => {
      comment.showReplyBox = false;
      if (comment.childComment && comment.childComment.length) {
        closeAllReplyBoxes(comment.childComment);
      }
    });
  }

  // 重置活动评论框
  setActiveComment(null);
  // 聚焦到父评论输入框
  myInput.value.focus();
}

// 添加一个辅助函数来关闭所有回复框
function closeAllReplyBoxes(comments: any[]) {
  comments.forEach(comment => {
    comment.showReplyBox = false;
    if (comment.childComment && comment.childComment.length) {
      closeAllReplyBoxes(comment.childComment);
    }
  });
}

// 添加设置活动评论框的方法
function setActiveComment(id: number | null) {
  // @ts-ignore
  activeCommentId.value = id;
}


// 添加父评论
function addParentComment() {
  if (textarea.value === '') {
    ElMessage.error("评论内容不能为空");
    return
  }
  let data = {
    type: props.type,
    typeId: props.typeId,
    commentContent: textarea.value
  }
  addComment(data).then((res: any) => {
    if (res.code === 200) {
      ElMessage.success("评论成功");
      if (res.data) {
        ElNotification({
          title: '评论成功',
          duration: 4000,
          type: 'warning',
          message: h('i', { style: 'color: teal' }, res.data),
        })
      }
      textarea.value = ''
      getComments(props.typeId, '1', String(pageSize.value))
    } else if (res.code === 1002) {
      ElMessage.error(res.msg);
    }
  })
}
</script>

<template>
  <div class="comments">
    <div v-if="isShowHeader">
      <div class="comments_title">
        <svg-icon name="comment" width="1.5em" height="1.5em" />
        <span>评论({{ commentsTotal }})</span>
      </div>
      <div class="form_container">
        <!-- 评论框 -->
        <textarea ref="myInput" class="textarea" v-model="textarea" placeholder="留下你的精彩评论吧！" />
        <div class="btn">
          <div>
            <EmojiPicker :popover-width="510" @select-emoji="handleEmojiSelect" @mousedown.stop @click.stop>
              <template #trigger>
                <div class="emoji_trigger_btn" @click.stop="handleParentEmojiButtonClick" @mousedown.stop>
                  <svg-icon name="emojis" class="emoji-icon" />
                </div>
              </template>
            </EmojiPicker>
          </div>
          <div>
            <el-button type="info" plain size="small" @click="isPreview = !isPreview">预览</el-button>
            <el-button type="success" plain size="small" @click="addParentComment">发布</el-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@use "./index.scss";

.more {
  display: flex;
  justify-content: center;
  margin-top: 1rem;

  .el-button {
    width: 100%;
  }
}

/* 确保表情按钮在评论组件中也有合适的间距 */
.emoji_trigger_btn {
  margin-right: 0.8rem;
}
</style>