<script lang="ts" setup>
import { Transition } from "vue";
import Menu from "../../../components/Layout/Menu/index.vue";
import SideBar from "../../../components/SideBar.vue";
</script>

<template>
  <div>
    <Menu margin-top="4%" is-side-bar>
      <template v-slot:side>
        <SideBar />
      </template>
      <template v-slot:content>
        <router-view v-slot="{ Component }">
          <Transition name="el-fade-in-linear" mode="out-in">
            <component :is="Component" />
          </Transition>
        </router-view>
      </template>
    </Menu>
  </div>
</template>

<style lang="scss" scoped></style>