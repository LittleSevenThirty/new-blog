<script lang="ts" setup>
import { ref } from 'vue';
import Card from './Card.vue';
import { getTagList } from '../apis/tag';
import { Tag } from '../apis/tag/type';
import { ElMessage } from 'element-plus';

const loading = ref(false);
const tags = ref<Tag[]>();

function loadContent() {
  loading.value = true;
  getTagList().then((res: any) => {
    if (res.code == 200 && res.data != null) {
      // 按照文章数量截取前10
      tags.value = res.data.sort((a: Tag, b: Tag) => {
        return b.articleCount - a.articleCount;
      }).slice(0, 10);
    }
    // 数据加载完毕
    loading.value = false;
  }).catch((error) => {
    ElMessage.error("加载标签数据失败：" + error);
    loading.value = false;
  });
}
</script>

<template>
  <Card title="热门标签" prefix-icon="tag" :is-rotate="true" :is-scale="true" v-view-request="{ callback: loadContent }">

  </Card>
</template>

<script lang="scss" scoped></script>