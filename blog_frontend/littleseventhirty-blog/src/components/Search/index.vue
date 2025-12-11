<script setup lang="ts">
import { Ref, ref, reactive } from 'vue';
import router from '../../router/index.ts';
import { Search, Delete, Loading } from '@element-plus/icons-vue';
import { searchArticleByContent } from '../../apis/article/index';
import { ArticleSearch, HotArticle } from '../../apis/article/type';
import { useLocalStorage } from '@vueuse/core';
import { escapeRegExp } from '../../utils/tools';
import SvgIcon from '../SvgIcon/index.vue'
import useWebsiteStore from '../../pinia/store/modules/website.ts';

// 声明我的事件
const emits = defineEmits(['isShowSearch'])

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

// 定位到搜索文章
function clickSearchResult(event: any, articleId: string) {

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
              <Loading />
            </el-icon>
            <span>换一换</span>
          </div>
        </div>
        <div>
          <div v-for="hot in hotList" v-bind:key="hot.articleId" v-on:click="() => {
            // 发出我触发了isShowSearch事件
            emits('isShowSearch');
            $router.push('/article/'+hot.articleId);
          }">
            {{ hot.articleTitle }}
            <div>
              <svg t="1765170512296" class="icon" viewBox="0 0 1024 1024" version="1.1"
                xmlns="http://www.w3.org/2000/svg" p-id="7884" width="200" height="200">
                <path d="M517.12 517.12m-460.8 0a460.8 460.8 0 1 0 921.6 0 460.8 460.8 0 1 0-921.6 0Z" fill="#FCE8D8"
                  p-id="7885"></path>
                <path
                  d="M505.856 226.304c-16.384-5.12-32.768 6.144-34.816 23.04-5.12 37.376-36.864 91.136-91.136 146.944-16.384 16.896-34.816 32.256-53.248 46.592-43.52 34.304-74.752 88.576-74.752 156.16 0 75.776 48.128 203.264 283.648 203.264s269.824-154.112 269.824-203.264c0-45.056-9.728-137.728-83.968-190.464-10.24-7.168-24.576-4.096-30.72 7.168-6.656 12.288-15.36 23.552-37.376 37.376-5.632 3.584-12.8-1.536-11.776-8.192 12.8-59.904 12.288-172.544-135.68-218.624z"
                  fill="#EF885B" p-id="7886"></path>
                <path
                  d="M556.032 679.936c32.768-3.072 57.856-31.232 55.296-62.976 0-1.536 0-2.56-0.512-3.584-3.584-25.088-15.36-43.52-44.032-68.608-21.504-18.944-19.968-53.76-17.408-73.728 1.024-6.656-5.12-11.776-11.264-9.216-31.232 12.288-103.936 47.104-108.032 113.664-2.048 28.672 6.656 53.76 18.944 74.24 9.728 16.384 26.624 27.136 45.568 29.696 10.752 1.536 23.04 2.048 35.84 2.048 9.728-0.512 17.92-1.024 25.6-1.536z"
                  fill="#FCE8D8" p-id="7887"></path>
              </svg>
              <span>{{ hot.visitedCount }}</span>
            </div>
          </div>
        </div>
      </div>
    </template>
    <template v-else>
      <div v-if="articleSearchList?.length === 0" style="text-align:center;padding-top:2rem">
        <span style="font-size:12px;color:gray;">
          {{ optionsValue === '标题' ? '请输入要搜索的内容' : '内容搜索每分钟只能搜索5次' }}
        </span>
      </div>
      <div class="search_result">
        <template v-if="searchValue && optionsValue === '标题'">
          <div v-for="item in articleSearchList" v-bind:key="item.articleId"
            @mousedown="clickSearchResult($event, item.articleId)">
            <div class="search_result_item">
              <div>
                <div v-html="item.hilightedTitle"></div>
                <div class="text-xs mt-1 dark:text-[#A3A3A3] p-1">
                  <el-tag type="info" size="small" effect="light" class="mr-2">
                    {{ item.categoryName }}
                  </el-tag>
                </div>
              </div>
              <div class="flex space-x-2 text-xs text-[#475569] items-center justify-center">
                <svg t="1765170512296" class="icon" viewBox="0 0 1024 1024" version="1.1"
                  xmlns="http://www.w3.org/2000/svg" p-id="7884" width="200" height="200">
                  <path d="M517.12 517.12m-460.8 0a460.8 460.8 0 1 0 921.6 0 460.8 460.8 0 1 0-921.6 0Z" fill="#FCE8D8"
                    p-id="7885"></path>
                  <path
                    d="M505.856 226.304c-16.384-5.12-32.768 6.144-34.816 23.04-5.12 37.376-36.864 91.136-91.136 146.944-16.384 16.896-34.816 32.256-53.248 46.592-43.52 34.304-74.752 88.576-74.752 156.16 0 75.776 48.128 203.264 283.648 203.264s269.824-154.112 269.824-203.264c0-45.056-9.728-137.728-83.968-190.464-10.24-7.168-24.576-4.096-30.72 7.168-6.656 12.288-15.36 23.552-37.376 37.376-5.632 3.584-12.8-1.536-11.776-8.192 12.8-59.904 12.288-172.544-135.68-218.624z"
                    fill="#EF885B" p-id="7886"></path>
                  <path
                    d="M556.032 679.936c32.768-3.072 57.856-31.232 55.296-62.976 0-1.536 0-2.56-0.512-3.584-3.584-25.088-15.36-43.52-44.032-68.608-21.504-18.944-19.968-53.76-17.408-73.728 1.024-6.656-5.12-11.776-11.264-9.216-31.232 12.288-103.936 47.104-108.032 113.664-2.048 28.672 6.656 53.76 18.944 74.24 9.728 16.384 26.624 27.136 45.568 29.696 10.752 1.536 23.04 2.048 35.84 2.048 9.728-0.512 17.92-1.024 25.6-1.536z"
                    fill="#FCE8D8" p-id="7887"></path>
                </svg>
                <span>{{ item.visitedCount }}</span>
              </div>
            </div>
          </div>
        </template>
        <template v-if="searchValue && optionsValue === '内容'">
          <div v-for="item in articleSearchList" v-bind:key="item.articleId"
            @mousedown="clickSearchResult($event, item.articleId)">
            <div class="search_result_item">
              <div v-html="item.articleTitle"></div>
              <div class="text-xs mt-1 dark:text-[#A3A3A3] p-1 flex">
                <div>
                  <el-tag type="info" size="small"  effect="light" class="mr-2">
                    {{ item.categoryName }}
                  </el-tag>
                </div>
                <div v-html="item.articleContent">
                </div>
              </div>
              <div class="flex space-x-2 text-xs text-[#475569] items-center justify-center">
                <svg t="1765170512296" class="icon" viewBox="0 0 1024 1024" version="1.1"
                  xmlns="http://www.w3.org/2000/svg" p-id="7884" width="200" height="200">
                  <path d="M517.12 517.12m-460.8 0a460.8 460.8 0 1 0 921.6 0 460.8 460.8 0 1 0-921.6 0Z" fill="#FCE8D8"
                    p-id="7885"></path>
                  <path
                    d="M505.856 226.304c-16.384-5.12-32.768 6.144-34.816 23.04-5.12 37.376-36.864 91.136-91.136 146.944-16.384 16.896-34.816 32.256-53.248 46.592-43.52 34.304-74.752 88.576-74.752 156.16 0 75.776 48.128 203.264 283.648 203.264s269.824-154.112 269.824-203.264c0-45.056-9.728-137.728-83.968-190.464-10.24-7.168-24.576-4.096-30.72 7.168-6.656 12.288-15.36 23.552-37.376 37.376-5.632 3.584-12.8-1.536-11.776-8.192 12.8-59.904 12.288-172.544-135.68-218.624z"
                    fill="#EF885B" p-id="7886"></path>
                  <path
                    d="M556.032 679.936c32.768-3.072 57.856-31.232 55.296-62.976 0-1.536 0-2.56-0.512-3.584-3.584-25.088-15.36-43.52-44.032-68.608-21.504-18.944-19.968-53.76-17.408-73.728 1.024-6.656-5.12-11.776-11.264-9.216-31.232 12.288-103.936 47.104-108.032 113.664-2.048 28.672 6.656 53.76 18.944 74.24 9.728 16.384 26.624 27.136 45.568 29.696 10.752 1.536 23.04 2.048 35.84 2.048 9.728-0.512 17.92-1.024 25.6-1.536z"
                    fill="#FCE8D8" p-id="7887"></path>
                </svg>
                <span>{{ item.visitedCount }}</span>
              </div>
            </div>
          </div>
        </template>
      </div>
    </template>
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

    .search_result {
      width: 100%;
      height: 100%;
      overflow-y: scroll;
      overflow-x: hidden;
      margin-top: 1rem;

      .search_result_item {
        display: flex;
        align-items: center;
        justify-content: space-between
      }
    }

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