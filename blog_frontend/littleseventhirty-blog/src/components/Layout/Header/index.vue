<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { Close, Search as SearchIcon, Setting, Promotion, Sunny, Moon, Position } from '@element-plus/icons-vue';
import Search from '../../Search/index.vue';
import Menu from './HeaderMenu/index.vue';
import { useColorMode } from '@vueuse/core';
import useUserStore from '../../../pinia/store/modules/user';
import router from '../../../router';
import { logout } from '../../../apis/user';
import { REMOVE_TOKEN } from '../../../utils/auth';
import { ElMessage } from 'element-plus';
import MoveMenu from './MoveMenu/index.vue';
import DayNightToggleButton from '../../DayNightToggleButton/index.ts';

const userStore = useUserStore();

const dialogVisible = ref(false);

const mode = useColorMode();

const drawer = ref(false);

onMounted(async () => {
  try {
    // 获取指定标签元素，注册对应自定义组件
    if (!customElements.get('toggle-button')) {
      customElements.define('toggle-button', DayNightToggleButton);
    }
    await userStore.getInfo();
  } catch (e) {
    console.error("Error defining custom element or getting user info:", e);
  }
})

function changeToggle() {
  mode.value = mode.value == 'light' ? 'dark' : 'light';
}

// 登出函数
async function logoutSub() {
  console.log("点击了退出功能");
  const res = await logout() as any;
  if (res.code == 200) {
    REMOVE_TOKEN();
    userStore.userInfo = undefined;
    ElMessage.success("退出登录成功");
    router.push("/");
  }
  else {
    ElMessage.error("退出登录失败");
  }
}

// 后续改进
</script>

<template>
  <!-- 搜索框 -->
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
  <!-- PC端菜单栏 -->
  <div class="menu">
    <Menu />
  </div>
  <!-- 移动端适配 -->
  <div class="move_menu_nav">
    <div style="display: flex; justify-content: left; margin-left:2rem;">
      <div v-on:click="drawer = true" style="cursor: pointer;">
        <svg t="1768459357481" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
          p-id="4786" width="30" height="30">
          <path
            d="M106.666667 192a21.333333 21.333333 0 1 0 0 42.666667h85.333333a21.333333 21.333333 0 0 0 0-42.666667H106.666667z m0 298.666667a21.333333 21.333333 0 0 0 0 42.666666h85.333333a21.333333 21.333333 0 0 0 0-42.666666H106.666667z m0 298.666666a21.333333 21.333333 0 0 0 0 42.666667h85.333333a21.333333 21.333333 0 0 0 0-42.666667H106.666667zM320 192a21.333333 21.333333 0 0 0 0 42.666667h597.333333a21.333333 21.333333 0 0 0 0-42.666667H320z m0 298.666667a21.333333 21.333333 0 0 0 0 42.666666h597.333333a21.333333 21.333333 0 0 0 0-42.666666H320z m0 298.666666a21.333333 21.333333 0 0 0 0 42.666667h597.333333a21.333333 21.333333 0 0 0 0-42.666667H320z"
            fill="#3D3D3D" p-id="4787"></path>
        </svg>
      </div>
      <!-- 移动端日夜切换功能 -->
      <div style="margin-left: 2rem;">
        <toggle-button move="true" @change="changeToggle" size="1"></toggle-button>
      </div>
    </div>
    <!-- 搜索按钮 -->
    <div class="right_nav">
      <div class="search" @click="dialogVisible = true" style="margin-right: 1rem;margin-top: 0.5rem;">
        <svg t="1768462145827" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
          p-id="5197" width="30" height="30">
          <path
            d="M922.112 800.597333l-127.146667-127.146666c14.506667-21.333333 26.965333-44.032 37.034667-67.925334 7.338667-17.408 13.482667-35.669333 18.090667-54.101333 8.021333-31.573333 11.946667-64.170667 11.946666-96.938667C861.184 240.64 686.592 65.877333 472.746667 65.194667h-1.365334c-103.253333 0-200.021333 40.106667-272.896 112.810666C125.44 251.050667 85.333333 348.501333 85.674667 452.266667c0.682667 213.845333 175.445333 388.437333 389.290666 389.290666h1.365334c51.2 0 101.034667-9.898667 147.968-29.354666 17.408-7.168 25.6-27.136 18.432-44.544a33.9456 33.9456 0 0 0-44.544-18.432c-38.912 16.213333-79.872 24.917333-123.050667 24.064-176.469333-0.682667-320.512-144.725333-321.194667-321.365334-0.341333-85.333333 32.597333-165.546667 92.842667-225.621333 59.904-59.904 139.605333-92.842667 224.597333-92.842667h1.194667c176.469333 0.682667 320.682667 144.725333 321.194667 321.194667 0 27.136-3.242667 53.930667-9.728 79.872-3.925333 15.189333-8.874667 30.037333-14.848 44.544-11.093333 26.624-25.941333 51.541333-43.861334 74.069333-0.512 0.512-1.194667 1.194667-1.706666 1.877334-11.093333 13.482667-10.069333 33.450667 2.218666 45.738666l148.138667 147.968c6.656 6.656 15.36 10.069333 24.064 10.069334s17.408-3.413333 24.064-10.069334c13.312-13.312 13.312-34.816 0-48.128z"
            fill="#231815" p-id="5198"></path>
        </svg>
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
              <div style="font-size:14px;color:#363636;margin-top:3px;" v-if="userStore.userInfo?.registerType == 0">
                {{ userStore.userInfo?.email }}
              </div>
              <div style="font-size:14px;color:#363636;margin-top:3px;" v-else>
                {{ userStore.userInfo?.registerType === 1 ? "gitee" : "github" }}
              </div>
            </div>
            <el-dropdown>
              <el-avatar v-bind:src="userStore.userInfo?.avatar" style="margin-right:2rem"></el-avatar>
              <template v-slot:dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="router.push('/setting')">
                    <template v-slot:default>
                      <el-icon>
                        <Setting />
                      </el-icon>
                      <span>个人设置</span>
                    </template>
                  </el-dropdown-item>
                  <el-dropdown-item @click="logoutSub()">
                    <template v-slot:default>
                      <el-icon>
                        <Promotion />
                      </el-icon>
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
  </div>
  <div>
    <el-drawer v-model="drawer" direction="ltr" size="40%" :show-close="false">
      <template v-slot:header>
        <div style="display: flex; justify-content: center;">
          <span style="font-size:2rem;">
            导航
          </span>
          <Position style="width:2em;height: 2em;margin-top: 10px;" />
        </div>
        <el-button :icon="Close"
          style="background: none;font-size: 1.5rem;width: 30px;border: none;margin-bottom: 10px;"
          @click="drawer = false" />
      </template>
      <template v-slot:default>
        <MoveMenu v-on:update:close-drawer="drawer = false" />
      </template>
    </el-drawer>
  </div>
</template>

<style scoped lang="scss">
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

.user_info {
  &:hover {
    cursor: pointer;
  }
}

// 手机端
@media screen and (max-width:780px) {
  .menu {
    display: none;
  }

  .move_menu_nav {
    display: flex;
    z-index: 999;
    width: 100vw;
    top: 0;
    position: fixed;
    height: 45px;
    box-sizing: border-box;
    align-items: center;
    justify-content: space-between;

    .right_nav {
      display: flex;

      .search {
        display: flex;
        justify-content: center;
        align-items: center;
        margin-right: 20px;
        transition: transform 0.3s linear;
        cursor: pointer;

        &:hover {
          transform: scale(1.1);
        }
      }
    }
  }
}

// pc端
@media screen and (min-width:780px) {
  .move_menu_nav {
    display: none;
  }
}
</style>