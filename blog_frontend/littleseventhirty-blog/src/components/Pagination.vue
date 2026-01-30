<script lang="ts" setup>
import usePaginationStore from '../pinia/store/modules/pagination';

const paginationStore = usePaginationStore();
</script>

<template>
  <div class="pag">
    <el-pagination @current-change="paginationStore.articlePagination.current = $event"
      v-model:currentPage="paginationStore.articlePagination.current"
      :page-size="paginationStore.articlePagination.pageSize" :hide-on-single-page="true" layout="prev, pager, next"
      :total="paginationStore.articlePagination.total" background>
    </el-pagination>
  </div>
</template>

<style lang="scss" scoped>
.pag {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>