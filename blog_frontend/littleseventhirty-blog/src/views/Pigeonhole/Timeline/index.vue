<script setup lang="ts">
import Menu from "../../../components/Layout/Menu/index.vue";
import Banner from "../../../components/Banner.vue";
import { getTimeline } from "../../../apis/article/index.ts";
import { computed, nextTick, onMounted, ref } from "vue";

const items: any = ref([]);
const shellRef = ref<HTMLElement | null>(null);

function handleData(data: any) {
  data = data.map((item: any) => {
    // 去掉特殊字符
    item.articleContent = item.articleContent.replace(/[*#>`~\-\\[\]()\s]|(\n\n)/g, '');
    // 截取前50字符内容
    item.articleContent = item.articleContent.substring(0, 60) + "...";
    // 去掉时分秒
    item.createTime = item.createTime.substring(0, 10);
    return item;
  });
  const groupedArticles = computed(() => {
    return data.reduce((groups: any, article: any) => {
      const year = new Date(article.createTime).getFullYear();
      // 创建新分组
      if (!groups[year]) {
        groups[year] = [];
      }
      groups[year].push(article);
      return groups;
    })
  }, {});
  return groupedArticles.value;
}

onMounted(async () => {
  const res: any = await getTimeline();
  if (res.code != 200) return;
  items.value = handleData(res.data);
  await nextTick(() => {
    const shell = shellRef.value;
    if (!shell) return;
    const itemElements = shell.querySelectorAll(".item");
    const itemArray = Array.from(itemElements);

    // 初始设置第一个时间轴激活，并将背景图设置成为第一个项目图片
    itemArray[0].classList.add("item_active");
    // @ts-ignore
    shell.style.backgroundImage = `url(${itemArray[0].querySelector('.img')?.src})`;

    // 页面滚动触发滚动事件
    window.addEventListener("scroll", () => {
      const poosition = window.pageYOffset;
      itemArray.forEach((item: any, index) => {
        // 上边距顶部距离
        const min = item.offsetTop;
        // 下边距顶距离
        const max = item.offsetHeight + item.offsetTop;
      })
    })
  })
});
</script>

<template>
  <div>
    <Menu only-father-container>
      <template v-slot:banner>
        <Banner title="时间轴" subtitle="Timeline"></Banner>
      </template>
      <template v-slot:content>
        <div class="shell" ref="shellRef">
          <div class="timeline">
            <template v-for="(item, year) in items" :key="item?.articleId">
              <div class="year">--{{ year }}--</div>
              <div class="item" :data-text="i.createTime" :click="$router.push(`/article/${i.articleId}`)"
                v-for="i in item">
                <div class="content">
                  <img class="img" :src="i.articleCover" />
                  <h2 class="content_title">{{ i.articleTitle }}</h2>
                  <p class="content_desc">{{ i.articleContent }}</p>
                </div>
              </div>
            </template>
          </div>
        </div>
      </template>
    </Menu>
  </div>
</template>

<style lang="scss" scoped>
@use "./index.scss";
</style>