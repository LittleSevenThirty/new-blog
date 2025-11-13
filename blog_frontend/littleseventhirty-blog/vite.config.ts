import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path';
import Components from 'unplugin-vue-components/vite';
import {ElementPlusResolver} from 'unplugin-vue-components/resolvers'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    Components({
      // 重要配置：指定类型声明文件输出路径
      dts: "src/types/components.d.ts", 
      
    }),// 添加unplugin-vue-components配置
  ],
  resolve: {
    alias: {
      "@": path.resolve(__dirname,"./src"),
    },
  },
  
})
