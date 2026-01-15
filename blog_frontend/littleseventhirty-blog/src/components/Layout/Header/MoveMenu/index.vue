<script setup>
import { defineEmits } from 'vue';
import { Clock, DocumentCopy, Files, Fries, Headset, HomeFilled, Link, Picture, Postcard, PriceTag, IceCreamRound } from '@element-plus/icons-vue'
const showMusic = import.meta.env.VITE_FRONTEND_URL;

const emit = defineEmits(["update:closeDrawer"]);

function clickGoto() {
  emit("update:closeDrawer");
}
</script>

<template>
  <div>
    <!-- 后续可更改为使用v-for动态拼接导航栏 -->
    <el-menu mode="vertical" router style="width: 100%; border: none;">
      <el-menu-item index="/" @click="clickGoto">
        <span>
          <el-icon>
            <HomeFilled />
          </el-icon>
          <span>首页</span>
        </span>
      </el-menu-item>
      <el-sub-menu :index="2">
        <template v-slot:title>
          <span>
            <el-icon>
              <Files />
            </el-icon>
            <span>归档</span>
          </span>
        </template>
        <el-menu-item index="/category" @click="clickGoto">
          <span>
            <el-icon>
              <DocumentCopy />
            </el-icon>
            <span>分类</span>
          </span>
        </el-menu-item>
        <el-menu-item index="/tags" @click="clickGoto">
          <span>
            <el-icon>
              <PriceTag />
            </el-icon>
            <span>标签</span>
          </span>
        </el-menu-item>
        <el-menu-item index="/timeline" @click="clickGoto">
          <span>
            <el-icon>
              <Clock />
            </el-icon>
            <span>时间轴</span>
          </span>
        </el-menu-item>
      </el-sub-menu>
      <el-sub-menu :index="3">
        <template v-slot:title>
          <el-icon>
            <IceCreamRound />
          </el-icon>
          其他
        </template>
        <el-menu-item index="/tree-hole" @click="clickGoto">
          <span>
            <el-icon>
              <Fries />
            </el-icon>
            <span>树洞</span>
          </span>
        </el-menu-item>
        <el-menu-item index="/message" @click="clickGoto">
          <span>
            <el-icon>
              <Postcard />
            </el-icon>
            <span>留言板</span>
          </span>
        </el-menu-item>
        <el-menu-item index="/about" @click="clickGoto">
          <span>
            <el-icon>
              <Link />
            </el-icon>
            <span>关于</span>
          </span>
        </el-menu-item>

      </el-sub-menu>
      <el-menu-item index="/link" @click="clickGoto">
        <span>
          <el-icon>
            <Link />
          </el-icon>
          <span>友链</span>
        </span>
      </el-menu-item>
      <el-menu-item index="/music" @click="clickGoto" v-if="showMusic">
        <span>
          <el-icon>
            <Headset />
          </el-icon>
          <span>音乐</span>
        </span>
      </el-menu-item>
      <el-menu-item index="/photo" @click="clickGoto">
        <span>
          <el-icon>
            <Picture />
          </el-icon>
          <span>相册</span>
        </span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<style scoped lang="scss"></style>