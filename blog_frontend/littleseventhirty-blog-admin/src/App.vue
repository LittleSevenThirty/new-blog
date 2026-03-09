<script setup lang="ts">
import { useAppStore } from '~/stores/app'
import { storeToRefs } from 'pinia'
import { useI18nLocale } from '~/composables/i18n-locale'
import TokenProvider from '~/components/token-provider/index.vue'

const appStore = useAppStore()
const { layoutSetting } = storeToRefs(appStore)
const theme = layoutSetting.theme
</script>

<template>
  <a-config-provider :theme="theme">
    <a-app class="h-full font-chinese antialiased">
      <TokenProvider>
        <RouterView />
      </TokenProvider>
    </a-app>
  </a-config-provider>
</template>
