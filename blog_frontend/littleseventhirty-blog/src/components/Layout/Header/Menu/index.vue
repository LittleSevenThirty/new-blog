<script setup lang="ts">
import { onMounted, onUnmounted, ref } from 'vue';
import { ArrowDownBold, Clock, Close, DocumentCopy, Files, Fries, Headset, HomeFilled, Link, Picture, Postcard, PriceTag, Moon, Sunny, Search as ElSearch, MoonNight, Setting, Promotion, IceCreamRound } from '@element-plus/icons-vue'
import useWebsiteStore from '../../../../pinia/store/modules/website';
import router from '../../../../router';
import { useColorMode } from '@vueuse/core';
import Search from '../../../Search/index.vue';
import useUserStore from '../../../../pinia/store/modules/user';
import { logout } from '../../../../apis/user';
import { REMOVE_TOKEN } from '../../../../utils/auth';
import { ElMessage } from 'element-plus';
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
// 日夜切换函数
function changeToggle(event: boolean) {
  mode.value = event ? "light" : "dark";
}
// 登出函数
async function logoutSub(){
  console.log("点击了退出功能");
  const res=await logout() as any;
  if(res.code==200){
    REMOVE_TOKEN();
    userStore.userInfo=undefined;
    ElMessage.success("退出登录成功");
    router.push("/");
  }
  else{
    ElMessage.error("退出登录失败");
  }
}

// 滚动相关函数（向下滚动不显示菜单栏，向上滚动显示菜单栏）
let lastScrollTop=0;
let scrollTimeout:number|undefined;
function scrollHandle(){
  // 获取当前滚动条和top之间的高度差
  const currentScrollTop=window.pageYOffset||document.documentElement.scrollTop;
  // console.log(currentScollTop);
  // 控制菜单显示和透明度
  isMenuVisible.value=currentScrollTop<=lastScrollTop; // 原是currentScrollTop>=0取反
  isTransParent.value=currentScrollTop===0;
  lastScrollTop=currentScrollTop<= 0?0:currentScrollTop;
}

// 防抖
function debounceBackground(){
  if(scrollTimeout){
    clearTimeout(scrollTimeout)
  }
  scrollTimeout=window.setTimeout(()=>{
    const currentScrollTop=window.pageYOffset||document.documentElement.scrollTop;
    isTransParent.value=currentScrollTop===0;
  },100);
}

onMounted(()=>{
  window.addEventListener("scroll",scrollHandle);
  window.addEventListener("scroll",debounceBackground);
});

onUnmounted(()=>{
  window.addEventListener("scroll",scrollHandle);
  window.addEventListener("scroll",debounceBackground);
});
</script>

<template>
  <div style="height: 6000px; background: linear-gradient(to bottom, white, black);">
  滚动测试区域（足够高）
  </div>
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
  <nav v-bind:class="{ 'hidden': !isMenuVisible, 'transparent': isTransParent }">
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
                <IceCreamRound/>
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
    <div id="menu_right">
      <!-- 日夜切换，使用element-plus的switch组件，会触发change事件-->
      <div style="margin-right: 2rem; margin-top: -0.25rem;">
        <toggle-button @change="changeToggle" size="1"></toggle-button>
      </div>
      <!-- 搜索按钮 -->
      <div id="search_button" style="margin-left:1rem;">
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
          <div style="display:flex">
            <div class="profile">
              <div style="font-size:15px;font-weight: bold;color:black">{{ userStore.userInfo?.username }}</div>
              <div style="font-size:14px;color:#363636;margin-top:3px;" v-if="userStore.userInfo?.registerType==0">
                {{ userStore.userInfo?.email}}
              </div>
              <div style="font-size:14px;color:#363636;margin-top:3px;" v-else>
                {{ userStore.userInfo?.registerType===1?"gitee":"github" }}
              </div>
            </div>
            <el-dropdown>
              <el-avatar v-bind:src="userStore.userInfo?.avater" style="margin-right:2rem"></el-avatar>
              <template v-slot:dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="router.push('/setting')">
                    <template v-slot:default>
                      <el-icon><Setting/></el-icon>
                      <span>个人设置</span>
                    </template>
                  </el-dropdown-item>
                  <el-dropdown-item @click="logoutSub()">
                    <template v-slot:default>
                      <el-icon><Promotion/></el-icon>
                      <span>退出登录</span>
                    </template>
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
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
    top: -50px; // 隐藏菜单
  }

  &.transparent {
    background-color: transparent;  // 效果没实现
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