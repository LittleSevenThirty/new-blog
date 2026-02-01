<!-- 侧边栏组件 -->
<script lang="ts" setup>
import InfoCard from './InfoCard.vue';
</script>

<template>
  <div>
    <div>
      <InfoCard/>
    </div>
    <div class="anouncement_container">
      <Card></Card>
    </div>
  </div>
</template>

<style lang="scss">
// 二次元少女，或者说live-2D
.anouncement_container{
  position:relative;
  margin-top:70px;
}
</style>