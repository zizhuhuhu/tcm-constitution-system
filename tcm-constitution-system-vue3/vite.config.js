import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver} from 'unplugin-vue-components/resolvers'
// https://vite.dev/config/
export default defineConfig({
  server: { //解决跨域问题
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ''),
      },
    }
  },
  
  plugins: [
    AutoImport({
      resolvers: [ElementPlusResolver()], //自动导入Element Plus的API
    }),
    Components({
      resolvers: [ElementPlusResolver()], //自动导入Element Plus组件
    }),
    vue(),
    vueDevTools(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)) //将@映射到src目录
    },
  },
})
