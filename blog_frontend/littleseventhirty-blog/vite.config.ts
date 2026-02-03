import { ConfigEnv, defineConfig, loadEnv } from "vite";
import vue from "@vitejs/plugin-vue";
import path from "path";
import Components from "unplugin-vue-components/vite";
import {createSvgIconsPlugin} from "vite-plugin-svg-icons";


// 自定义元素标签集合
const customElements = new Set(['toggle-button']);

// https://vite.dev/config/
export default defineConfig(({ mode }: ConfigEnv) => {
  const env = loadEnv(mode, process.cwd());
  return {
    plugins: [
      vue({
        template: {
          compilerOptions: {
            // 排除自定义元素标签
            isCustomElement: (tag) => customElements.has(tag)
          }
        }
      }),
      Components({
        // 重要配置：指定类型声明文件输出路径
        dts: "src/types/components.d.ts",
        // 其它配置1：指定需要扫描的组件目录（插件会扫描这些文件下的.vue文件）默认src/components
        dirs: ["src/components"],
        // 其它配置2：解析器配置=》对于其它第三方组件库，需要对应的解析器
        resolvers: [],
      }), // 添加unplugin-vue-components配置
      createSvgIconsPlugin({
        // 指定需要缓存的图标文件夹
        iconDirs: [path.resolve(process.cwd(), 'src/assets/icons')],
        // symbolId的格式，就是css的#标识
        symbolId: 'icon-[name]',
      })
    ],
    resolve: {
      alias: {
        "@": path.resolve(__dirname, "./src"),
      },
    },
    server: {
      port: 70,
      host: '0.0.0.0',
      proxy: {
        '/api': {
          target: `${env.VITE_SERVE}`,
          changeOrigin: true,
          rewrite: (path) => path.replace(/^\/api/, '')
        }
      }
    },
    css: {
      // 预处理css代码
      preprocessorOptions: {
      }
    }
  };
});

// https://vite.dev/config/
// export default defineConfig({
//   plugins: [
//     vue(),
//     Components({
//       // 重要配置：指定类型声明文件输出路径
//       dts: "src/types/components.d.ts",
//       // 其它配置1：指定需要扫描的组件目录（插件会扫描这些文件下的.vue文件）默认src/components
//       dirs: [
//         "src/components",
//       ],
//       // 其它配置2：解析器配置=》对于其它第三方组件库，需要对应的解析器
//       resolvers: [

//       ]
//     }),// 添加unplugin-vue-components配置
//   ],
//   resolve: {
//     alias: {
//       "@": path.resolve(__dirname,"./src"),
//     },
//   },
// })
