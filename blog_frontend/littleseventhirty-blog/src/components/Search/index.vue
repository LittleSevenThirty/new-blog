<script setup lang="ts">
import { Ref, ref, reactive } from 'vue';
import { Search, Delete,Loading } from '@element-plus/icons-vue';
import { searchArticleByContent } from '../../apis/article/index';
import { ArticleSearch,HotArticle } from '../../apis/article/type';
import { useLocalStorage } from '@vueuse/core';
import { escapeRegExp } from '../../utils/tools';
import SvgIcon from '../SvgIcon/index.vue'
import useWebsiteStore from '../../pinia/store/modules/website.ts';

// 搜索内容
const searchValue = ref('');

// 控制placeholder显示内容
const optionsValue = ref('标题');

// 分段控制显示内容
const options = ['标题', '内容'];

// 控制搜索历史列表展示
const showSearch = ref(true);

// el-input的名字
const inputRef: Ref<HTMLInputElement | null> = ref(null);

// 搜索历史记录存放
const searchHistoryList = useLocalStorage<string[]>('searchHistoryList', ['hello', 'world']);

// 文章搜索列表
const articleSearchList = ref<Array<ArticleSearch>>([]);

// 热门文章结合
const hotList = ref<Array<HotArticle>>([]);

// 组织类似Element-ui配套form组件按下enter提交事件，需要添加.native
function handleSearch(_: any, isAutoFocus: boolean = false) {
  // 可以进行搜索
  if (searchValue.value && optionsValue.value === '内容') {
    const safeStr = escapeRegExp(searchValue.value);
    // console.log(isAutoFocus)
    if (!isAutoFocus) {
      // 历史记录里没有
      searchHistoryList.value.push(searchValue.value);
    }
    searchArticleByContent(searchValue.value).then((res: any) => {
      // 补充：这里还有其它内容需要补充，比如怎么判断需不需要res数据
      articleSearchList.value = res.data;

      articleSearchList.value = articleSearchList.value.map((item: ArticleSearch) => {
        const regex = new RegExp(`(${safeStr})`, 'gi');
        const articleContent = item.articleContent.replace(regex, '<span class="highlight">$1</span>')
        return {
          ...item,
          articleContent
        }
      })
    })
  }
}

// 标题数据pinia存储站
const websiteStore = useWebsiteStore();

// 处理输入框获取焦点函数
async function handleFocus(event: any) {
  if ((searchValue.value && optionsValue.value === '标题') && !websiteStore.articleSearch) {
    await websiteStore.getArticleTitleList();
  }
  showSearch.value = false;
}

// 处理输入框失去焦点函数
function handleBlur(event: any) {
  showSearch.value = true;
}

// 删除历史记录函数
function delAllHistory(event: any) {
  console.log("删除历史记录")
}

// 历史搜索
function historySearch(p: any) {
  console.log(p);
}

</script>

<template>
  <div class="content_container">
    <div class="search">
      <el-form @submit.native.prevent>
        <el-input ref="inputRef" :placeholder="optionsValue === '标题' ? '请输入搜索内容' : '回车进行内容搜索'" v-model="searchValue"
          :prefix-icon="Search" v-on:keyup.enter.native="handleSearch($event)" v-on:focus="handleFocus($event)"
          v-on:blur="handleBlur($event)" clearable>
          <template v-slot:prefix>
            <div style="width:0;">
              <SvgIcon name="search" width="20" height="20" />
            </div>
          </template>
          <template v-slot:append>
            <div class="custom-style">
              <el-segmented v-model="optionsValue" :options="options" size="small" block />
            </div>
          </template>
        </el-input>
      </el-form>
    </div>
    <template v-if="showSearch">
      <!-- 搜索历史 -->
      <div class="search_history">
        <div class="header_history">
          <div>搜索历史</div>
          <div class="event_history" v-on:click="delAllHistory($event)">
            <el-icon>
              <Delete />
            </el-icon>
            <span>清除记录</span>
          </div>
        </div>
        <!-- 历史记录 -->
        <div>
          <el-tag type="primary" style="margin: 5px;background-color: red;" v-for="(item) in searchHistoryList"
            :key="item" checked @click="historySearch(item)">
            {{ item }}
          </el-tag>
        </div>
        <!-- 热门推荐 -->
        <div class="header_history">
          <div>热门推荐</div>
          <div class="event_history" v-on:click="">
            <el-icon>
              <Loading/>
            </el-icon>
            <span>换一换</span>
          </div>
        </div>
        <div>
          <div v-for="hot in hotList" v-bind:key="hot.articleId" v-on:click="()=>{
            
          }">
          </div>
        </div>
      </div>
    </template>
    <template v-else></template>
  </div>
</template>

<style lang="scss" scoped>
@use "../../styles/mixin.scss" as *;

.content_container {
  height: 100%;

  // 搜索历史
  .search_history {
    width: 100%;
    height: 100px;
    background-color: black;

    .header_history {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 10px;
      font-size: 16px;
      font-weight: bold;
    }

    .event_history {
      @include flex;

      &:hover {
        cursor: pointer;
        color: #3599f0;
        transition: color 0.3s linear;
      }

      span {
        margin-left: 5px;
      }
    }
  }
}

:deep(.search) {
  padding: 0.5px 5px 0.5px 5px;
}

.custom-style .el-segmented {
  --el-segmented-item-selected-color: var(--el-bg-color);
  --el-segmented-item-selected-bg-color: #3599f0;
  --el-border-radius-base: 16px;
  font-size: 0.9em;
  color: grey;
}
</style>