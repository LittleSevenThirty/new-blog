<template>
  <!-- 搜索框 -->
  <div class="seacher_dialog_container">
    <el-button type="primary" size="default" @click="dialogVisible"></el-button>
    <el-dialog
    v-model="dialogVisible"
    >
    <span></span>
    <template #footer>
    <span>
    <el-button @click="dialogVisible = false">取消</el-button>
    <el-button type="primary" @click="dialogVisible=false">确认</el-button>
    </span>
    </template>
    </el-dialog>
  </div>
  <!-- 菜单栏 -->
  <div></div>
  <!-- 移动端适配 -->
  <div></div>
  <div></div>
</template>

<script setup lang="ts">
import { ref } from 'vue';

const dialogVisible=ref(false);
</script>

<style scoped lang="scss">
.seacher_dialog_container {
  // 影响子组件的展示效果
  :deep(.el-dialog){
    overflow: auto;
    border-radius: 15rex;
    height: 70%;
  }

  // 扩展不同媒体展示效果
  @media screen and (max-width: 650px){
    :deep(.el-dialog){
      overflow: auto;
      margin-top:0;
      margin-bottom: 0;
      border-radius: 0;
      height: 100%;
      width:100vw;
    }
  }
}
</style>