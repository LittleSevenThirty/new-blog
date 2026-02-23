<script lang="ts" setup>
import { onMounted, Ref, ref, UnwrapRef } from 'vue';
import { Footers, MdEditor, ToolbarNames } from "md-editor-v3";
import "md-editor-v3/lib/style.css";  // md-editor-v3的样式要专门导入
import { useColorMode } from '@vueuse/core';
import { ElMessage } from 'element-plus';
import { addNewLeaveWord, getLeaveWordList } from "../../../../apis/leaveWord/index.ts";

const mode = useColorMode();
const isShow = ref(false);
const text = ref('');
const wordCount = ref(0); // 字数
// 页脚
const footers: (Footers | number)[] = [0, 1, '=', 'scrollSwitch'];
const leaveWordList = ref<Array<{
  content: string,
  nickName: string,
  avater: string,
  createTime: string
}>>([]);

// 工具栏
const toolbars: Ref<UnwrapRef<ToolbarNames[]>> = ref([
  'bold',
  'underline',
  'italic',
  'title',
  'strikeThrough',
  'quote',
  'unorderedList',
  'orderedList',
  'task',
  'codeRow',
  'code',
  'link',
  'image',
  'table',
  'pageFullscreen',
  'preview',
  'catalog',
]);

function mdContent(content: string) {
  if (content.length > 2000) {
    content = content.slice(0, 2000);
    ElMessage.warning("字数超出限制，已截取至前2000");
  }
  text.value = content;
  wordCount.value = content.length;
}

function showEditor() {
  isShow.value = true;
  // text.value = '';
}

// 新增留言
function addLeaveWord() {
  if (text.value === '') {
    ElMessage.warning("请输入内容");
    return;
  }
  addNewLeaveWord(text.value).then((res: any) => {
    if (res.code == 200) {
      ElMessage.success("留言成功");
      isShow.value = false;
      getLeaveWordListFunc();
    } else {
      ElMessage.error(res.msg);
    }
  });
}

function getLeaveWordListFunc() {
  getLeaveWordList().then((res: any) => {
    leaveWordList.value = res.data.map((item: any) => {
      item.content = item.content.replace(/[*#>`~\-\\[\]()\s]|(\n\n)/g, '');
      if (item.content.length > 35) {
        item.content = item.content.substring(0, 35) + "...";
      }
      return item;
    });
  })
}

onMounted(() => {
  getLeaveWordListFunc();
})
</script>

<template>
  <div class="bg">
    <!-- 占位 -->
    <div class="occupancyHeight"></div>
    <div id="title">
      <div class="title">
        <div>留言板</div>
        <el-button type="primary" size="default" plain @click="showEditor">留言</el-button>
      </div>
      <el-divider />
      <div class="title_content">
        <span>欢迎访问留言板板块！</span>
        <span>留言板是一个方便与其他用户交流、分享观点和意见的平台。通过留言板，您可以：</span>
        <span>1、留下自己的留言：无论是对特定主题的讨论、对某篇文章的评论，还是向其他用户提问，您都可以在留言板上发表自己的留言。</span>
        <span>2、回复其他用户：看到其他用户的留言后，您可以进行回复和互动，分享自己的观点，提供帮助或者表达共鸣。</span>
        <span>3、进行在线交流：留言板不仅是一个留言的平台，还是一个与其他用户进行实时交流的地方。您可以通过留言板与他人进行私聊，分享更多的讨论和信息。</span>
        <span>支持：普通文档，markdown</span>
      </div>
      <el-divider />
      <div id="drawer">
        <el-drawer size="75%" v-model="isShow" direction="rtl" :with-header="false">
          <div>
            <MdEditor v-model="text" :theme="mode" style="height:100vh;" :footers="footers" :toolbars="toolbars"
              no-upload-img v-on:on-change="mdContent($event)" :preview="false" scroll-auto :max-length="2000">
              <template v-slot:defFooters>
                <div style="height: 100%;display: flex;align-items: center">
                  <span style="margin: 0 1rem">字数：{{ wordCount }}</span>
                  <span>字数限制：2000</span>
                </div>
              </template>
            </MdEditor>
            <div style="margin-top:1rem;float:right;">
              <el-button type="primary" size="default" @click="addLeaveWord" plain>提交</el-button>
            </div>
            <div style="margin-top:1rem;margin-right: 1rem;float:right;">
              <el-button type="warning" size="default" @click="isShow = false" plain>取消</el-button>
            </div>
          </div>
        </el-drawer>
      </div>
    </div>
    <!-- 留言列表 -->
    <div id="leave_word_list">
      <div class="title">留言列表</div>
      <div class="form">
        <template v-for="item in leaveWordList">
          <el-card class="box_card" shadow="hover" v-slide-in :body-style="{ padding: '20px' }">
            <template #header>
              <div class="card_header">
                <span>
                  <el-avatar :src="item.avater" />
                </span>
                <span class="name">{{ item.nickName }}</span>
                <span class="time">{{ item.createTime }}</span>
              </div>
            </template>
            <div class="text">
              {{ item.content }}
            </div>
            <div class="bottom">

            </div>
            <!-- card body -->
          </el-card>
        </template>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@use "../../../../styles/variable.scss" as *;

.bg {
  background-color: var(--el-bg-color);

  .occupancyHeight {
    margin: 2rem 0;

    @media screen and (min-width:910px) {
      display: none;
    }
  }

  .title {
    font-size: 20px;
    font-weight: bold;
    margin-bottom: 20px;
    display: flex;
    justify-content: space-between;
  }

  .title_content {
    font-size: 0.8rem;
    font-weight: bold;
    color: #999;
    display: flex;
    flex-direction: column;
    background: var(--mao-bg-message);
    padding: 0.5rem;
    border-radius: $border-radius;
    margin-bottom: 1rem;

    span {
      margin-bottom: 1rem;
      line-height: 1rem;
    }
  }

  .box_card {
    margin-top: 1rem;
    font-size: 0.9rem;

    .card_header {
      display: flex;
      align-items: center;

      .name {
        margin-left: 0.5rem;
        font-size: 1.3rem;
      }

      .time {
        // 右对齐
        flex: 1;
        text-align: right;
        margin-left: 1rem;
        font-size: 0.85rem;
        color: #999;
      }
    }

    .text {
      margin-bottom: 1rem;

      .reply {
        margin-top: 3rem;
      }
    }

    .bottom {}
  }
}

:deep(.el-drawer) {
  @media (max-width: 1000px) {
    width: 70% !important;
  }

  @media (max-width: 600px) {
    width: 100% !important;
  }
}
</style>