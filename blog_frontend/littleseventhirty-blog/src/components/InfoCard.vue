<script lang="ts" setup>
import useWebsiteStore from "../pinia/store/modules/website.ts";

const websiteStore=useWebsiteStore();
</script>

<template>
<div v-slide-in class="info_container"></div>
</template>

<scirpt lang="scss" scoped>
.info_container{
  
}
</scirpt>