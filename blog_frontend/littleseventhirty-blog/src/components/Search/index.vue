<template>
  <div class="content_container">
    <div class="search">
      <el-form>
        <el-input ref="inputRef" :placeholder="optionsValue === '标题' ? '请输入搜索内容' : '回车进行内容搜索'" v-model="searchValue"
          :prefix-icon="Search" 
          v-on:keyup.enter.native="handleSearch($event)"
          v-on:focus="handleFocus($event)" 
          clearable />
      </el-form>
      <div>{{ searchValue }}</div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { Ref, ref, reactive } from 'vue';
import { Search } from '@element-plus/icons-vue';
import { searchArticleByContent } from '../../apis/article/index';
import {ArticleSearch} from '../../apis/article/type';
import { useLocalStorage } from '@vueuse/core';
import {escapeRegExp} from '../../utils/tools';

// 搜索内容
const searchValue = ref('');

// 控制placeholder显示内容
const optionsValue = ref('标题');

// el-input的名字
const inputRef: Ref<HTMLInputElement | null> = ref(null);

// 搜索历史记录存放
const searchHistoryList = useLocalStorage<string[]>('searchHistoryList', []);

// 文章搜索列表
const articleSearchList = ref<Array<ArticleSearch>>([]);

// 组织类似Element-ui配套form组件按下enter提交事件，需要添加.native
function handleSearch(_: any, isAutoFocus: boolean = false) {
  // 可以进行搜索
  if (searchValue.value && optionsValue.value === '内容') {
    const safeStr=escapeRegExp(searchValue.value);
    // console.log(isAutoFocus)
    if (!isAutoFocus) {
      // 历史记录里没有
      searchHistoryList.value.push(searchValue.value);
    }
    searchArticleByContent(searchValue.value).then((res:any)=>{
      // 补充：这里还有其它内容需要补充，比如怎么判断需不需要res数据
      articleSearchList.value=res.data;

      articleSearchList.value=articleSearchList.value.map((item:ArticleSearch)=>{
        const regex=new RegExp(`(${safeStr})`,'gi');
        const articleContent=item.articleContent.replace(regex,'<span class="highlight">$1</span>')
        return {
          ...item,
          articleContent
        }
      })
    })
  }
}

// 处理焦点聚集函数
function handleFocus(event:any){

}

</script>

<style lang="scss" scoped>
.content_container {
  height: 100%
}

:deep(.search) {
  padding: 0.5px 5px 0.5px 5px;
}
</style>