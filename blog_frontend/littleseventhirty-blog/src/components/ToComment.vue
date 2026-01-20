<!-- 提供到达评论区组件 -->
<script setup>
const backToComment = () => {
  window.scrollTo({
    top: document.documentElement.scrollHeight,
    behavior: 'smooth'
  })
}
</script>

<template>
  <div @click="backToComment">
    <el-tooltip content="查看评论" placement="right" effect="light">
      <div class="back_to_top">
        <svg t="1768921490092" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
          p-id="6316" width="30" height="30">
          <path
            d="M837.818182 476.315152C837.818182 314.957576 691.975758 186.181818 512 186.181818s-325.818182 130.327273-325.818182 290.133334c0 159.806061 145.842424 290.133333 325.818182 290.133333 10.860606 0 21.721212 0 32.581818-1.551515C605.090909 850.230303 682.666667 837.818182 732.315152 837.818182c-51.2-62.060606-9.309091-139.636364-9.309091-139.636364 69.818182-52.751515 114.812121-133.430303 114.812121-221.866666z"
            fill="#DDDDDD" p-id="6317"></path>
        </svg>
      </div>
    </el-tooltip>
  </div>
</template>

<style lang="scss" scoped>
.back_to_top {
  height: 30px !important;
  width: 30px !important;

  @media screen and (max-width:768px) {
    height: 30px !important;
    width: 30px !important;
  }
}
</style>