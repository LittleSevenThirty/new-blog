<template>
    <div class="" v-show="loadingStore.isLoading">
        <!-- 整个屏幕大的盒子 -->
        <div>
            <!-- 中间一个小盒子 -->
            <img>我需要一张图片</img>
            <span>
                <!-- 最后一个盒子 -->
                正在带着数据走向你~~
            </span>
        </div>
    </div>
</template>

<script lang="ts" setup>
import useLoadingStore from '../../pinia/store/modules/loading';
const loadingStore=useLoadingStore();
</script>

<style lang="scss" scoped>

</style>