<script setup lang="ts">
import Menu from "../../../components/Layout/Menu/index.vue";
import Banner from "../../../components/Banner.vue";
</script>

<template>
  <div>
    <Menu only-father-container>
      <template v-slot:banner>
        <Banner title="时间轴" subtitle="Timeline"></Banner>
      </template>
    </Menu>
  </div>
</template>

<style lang="scss" scoped></style>