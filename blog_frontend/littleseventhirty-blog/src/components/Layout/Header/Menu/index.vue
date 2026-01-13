<script setup lang="ts">
import { ref } from 'vue';
import { ArrowDownBold, Clock, Close, DocumentCopy, Files, Fries, Headset, HomeFilled, Link, Picture, Postcard, PriceTag, Moon, Sunny, Search as ElSearch, MoonNight } from '@element-plus/icons-vue'
import useWebsiteStore from '../../../../pinia/store/modules/website';
import router from '../../../../router';
import { useColorMode } from '@vueuse/core';
import Search from '../../../Search/index.vue';
import useUserStore from '../../../../pinia/store/modules/user';
// 判断是否该有音乐组件
//@ts-ignore
const showMusic = import.meta.env.VITE_FRONTEND_URL;

// 系统模式
const mode = useColorMode();
// pinia
const websiteStore = useWebsiteStore();
const userStore=useUserStore();

const dialogVisible = ref(false);

const isMenuVisible = ref(true);

const isTransParent = ref(true);

const themeChangeFlag = ref(true);

function changeToggle(event: boolean) {
  console.log(mode);
  mode.value = event ? "light" : "dark";
}
</script>

<template>
  <div class="seacher_dialog_container">
    <el-dialog v-model="dialogVisible" :show-close="false" :close-on-click-model="false" :lock-scroll="true"
      :close-on-press-escape="false">
      <template v-slot:header>
        <div style="display: flex;align-items: center;justify-content:space-between;">
          <span style="font-size:1.2rem;margin-left:1rem">搜索</span>
          <el-button :icon="Close" style="margin-right:1rem;background:none;font-size:1.5rem;width:30px;border:none;"
            @click="dialogVisible = false">
          </el-button>
        </div>
      </template>
      <Search v-on:isShowSearch="dialogVisible = false" />
    </el-dialog>
  </div>
  <nav v-bind:class="{ 'hidden': !isMenuVisible, 'transparent': isTransParent }" style="background:red;">
    <div id="menu_left">
      <div id="menus">
        <span id="blog_info">
          <a href="/">{{ websiteStore.webInfo?.websiteName }}</a>
        </span>
        <div class="menu_items">
          <!-- 首页 -->
          <div class="menu_item" @:click="router.push('/')">
            <span>
              <el-icon>
                <HomeFilled />
              </el-icon>
              <span>首页</span>
            </span>
          </div>
          <!-- 归档页 -->
          <div class="menu_item">
            <span>
              <el-icon>
                <Files />
              </el-icon>
              <span>归档</span>
              <el-icon class="arrow">
                <ArrowDownBold />
              </el-icon>
            </span>
            <ul class="menu_item_child">
              <li v-on:click="router.push('/category')">
                <span>
                  <el-icon>
                    <DocumentCopy />
                  </el-icon>
                  <span>分类</span>
                </span>
              </li>
              <li v-on:click="router.push('/tags')">
                <span>
                  <el-icon>
                    <PriceTag />
                  </el-icon>
                  <span>标签</span>
                </span>
              </li>
              <li v-on:click="router.push('/timeline')">
                <span>
                  <el-icon>
                    <Clock />
                  </el-icon>
                  <span>时间轴</span>
                </span>
              </li>
            </ul>
          </div>
          <!-- 其它页 -->
          <div class="menu_item">
            <span>
              <el-icon>
                <IceCreamRound />
              </el-icon>
              <span>其它</span>
              <el-icon class="arrow">
                <ArrowDownBold />
              </el-icon>
            </span>
            <ul class="menu_item_child">
              <li v-on:click="router.push('/tree-hole')">
                <span>
                  <el-icon>
                    <Fries />
                  </el-icon>
                  <span>树洞</span>
                </span>
              </li>
              <li v-on:click="router.push('/message')">
                <span>
                  <el-icon>
                    <Postcard />
                  </el-icon>
                  <span>留言板</span>
                </span>
              </li>
              <li v-on:click="router.push('/about')">
                <span>
                  <el-icon>
                    <Link />
                  </el-icon>
                  <span>关于</span>
                </span>
              </li>
            </ul>
          </div>
          <!-- 友链 -->
          <div class="menu_item" v-on:click="router.push('/link')">
            <span>
              <el-icon>
                <Link />
              </el-icon>
              <span>友链</span>
            </span>
          </div>
          <!-- 音乐 -->
          <div class="menu_item" v-on:click="router.push('/music')" v-if="showMusic">
            <span>
              <el-icon>
                <Headset />
              </el-icon>
              <span>音乐</span>
            </span>
          </div>
          <!-- 相册 -->
          <div class="menu_item" v-on:click="router.push('/photo')">
            <span>
              <el-icon>
                <Picture />
              </el-icon>
              <span>相册</span>
            </span>
          </div>
        </div>
      </div>
    </div>
    <div id="menu_right" style="background-color: red;">
      <!-- 日夜切换，使用element-plus的switch组件，会触发change事件-->
      <div style="margin-right: 1rem; margin-top: -0.25rem;">
        <el-switch v-model="themeChangeFlag" v-on:change="changeToggle($event)">
          <template v-slot:active-action>
            <el-icon><Sunny/></el-icon>
          </template>
          <template v-slot:inactive-action>
            <el-icon><MoonNight/></el-icon>
          </template>
        </el-switch>
      </div>
      <!-- 搜索按钮 -->
      <div id="search_button">
        <div class="search" v-on:click="dialogVisible = true">
          <el-icon>
            <ElSearch />
          </el-icon>
        </div>
      </div>
      <div class="user_info">
        <template v-if="!userStore.userInfo">
          <el-tooltip content="点击登陆" placement="bottom" effect="dark" class="box-item">
            <el-avatar @click="router.push('/welcome')" style="margin-right: 3rem">登录</el-avatar>
          </el-tooltip>
        </template>
        <template v-else>
          <!-- <div style="display:flex">
            <div class="profile">
              <div style="font-size:15px;font-weight: bold;color:black">{{ userStore.userInfo?.username }}</div>
              <div style="font-size:14px;color:#363636;margin-top:3px;" v-if="userStore.userInfo?.registerType==0">
                {{ userStore.userInfo?.email}}
              </div>
              <div style="font-size:14px;color:#363636;margin-top:3px;" v-else>
                {{ userStore.userInfo?.registerType===1?"gitee":"github" }}
              </div>
            </div>
            <el-dropdown trigger="click" size="default" split-button type="primary" @command="">
            title
              <template #dropdown>
                <el-dropdown-menu>
                <el-dropdown-item v-for="item in items"
                :key="item.key" :command="item.command">
              {{item.title}}
              </el-dropdown-item>
              </el-dropdown-menu>
              </el-dropdown>
              </template>
          </div> -->
        </template>
      </div>
    </div>
  </nav>
</template>

<style lang="scss" scoped>
.seacher_dialog_container {

  // 影响子组件的展示效果
  :deep(.el-dialog) {
    overflow: auto;
    border-radius: 10rex;
    height: 70%;
  }

  // 扩展不同媒体展示效果
  @media screen and (max-width: 650px) {
    :deep(.el-dialog) {
      overflow: auto;
      margin-top: 0;
      margin-bottom: 0;
      border-radius: 0;
      height: 100%;
      width: 100vw;
    }
  }
}

:deep(.el-dialog) {
  transition: all 0.3s;

  @media screen and (max-width: 1400px) {
    width: 45%;
  }

  @media screen and (max-width: 1000px) {
    width: 60%;
  }

  @media screen and (max-width: 760px) {
    width: 70%;
  }

  @media screen and (max-width: 600px) {
    width: 90%;
  }
}

nav {
  position: fixed; // 固定布局
  top: 0; // 离顶部距离
  display: flex; // 弹性布局
  height: 50px; // 盒子高度
  width: 100%; // 盒子宽度
  z-index: 999; // 盒子z轴层次
  background-color: rgba(255, 255, 255, 0.4); // 背景色
  backdrop-filter: blur(6px); // 背景模糊度blur
  transition: top 0.3s ease-in-out, background-color 0.3s ease-in-out; // 过渡属性

  &.hidden {
    top: -50px;
  }

  &.transparent {
    background-color: transparent;
  }

  #menu_left {
    flex: 2;

    #menus {
      display: flex; // 弹性布局
      justify-content: lfet; // 弹性布局下，子项在盒子的位置水平集中在左侧
      align-items: center; // 弹性布局下，子项在盒子的位置垂直集中在中间
      width: 100%;
      height: 100%;
      font-weight: bold; // 字体厚度

      #blog_info {
        width: 120px;
        margin: 0 10px; // 外边距上下0，左右10
      }

      .menu_items {
        width: 100%;
        height: 100%;
        display: flex;
        justify-content: left;

        .menu_item {
          height: 100%;
          width: 100px;
          position: relative;
          display: flex;
          justify-content: space-evenly;

          span .arrow {
            margin-left: 2.5px;
            transition: all 0.5s;
            transform: rotate(0deg);
            color: #409EFF;
          }

          &:hover {
            cursor: pointer;

            span .arrow {
              transform: rotate(180deg);
              color: #cc5de8;
            }

            .menu_item_child {
              display: block;
            }
          }

          span {
            display: flex;
            align-items: center;
            justify-content: space-evenly;
          }

          .menu_item_child {
            display: none;
            position: absolute;
            top: 50px;
            background: var(--el-bg-color);
            box-shadow: 0 2px 12px 0 var(--shadow-color); // 阴影设置
            border-radius: 5px;
            animation: slide_down 0.3s ease-out;

            // 设置菜单出现动画
            @keyframes slide_down {
              0% {
                opacity: 0%;
                transform: translateY(-10px);
              }

              100% {
                opacity: 100%;
                transform: translateY(0px);
              }
            }

            // 设置列表项效果
            li {
              display: flex;
              align-items: center;
              padding: 5px;
              justify-content: left;
              border-radius: 5px;

              &:li {
                cursor: pointer;
                background-color: #91a7ff;
              }
            }
          }
        }
      }
    }
  }

  #menu_right {
    flex: 1;
    display: flex;
    width: 100%;
    background-color: #fff;
    justify-content: right;
    align-items: center;

    #search_button .search {
      display: flex;
      justify-content: center;
      align-items: center;
      margin-right: 20px;
      cursor: pointer;
      transition: transform 0.3s linear;

      &:hover {
        transform: scale(1.1);
      }
    }

    .user_info {
      &:hover{
        cursor: pointer;
      }
    }
  }
}
</style>