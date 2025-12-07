
import { createApp } from 'vue'
import App from '@/App.vue'
import '@/assets/main.css'
//引入全局状态管理Pinia
import pinia from '@/stores'
//导入路由
import router from './router'
//导入全局路由守卫
import '@/permission'
//导入Elementplus图标
import * as ElementPlusIconVue from '@element-plus/icons-vue'
import 'animate.css'
import 'nprogress/nprogress.css'
import 'viewerjs/dist/viewer.css'
import VueViewer from 'v-viewer'
const app = createApp(App)
//应用路由
app.use(router)
app.use(VueViewer)
app.mount('#app')
//应用Pinia
app.use(pinia)
//引入图标
for(const [key, component] of Object.entries(ElementPlusIconVue)){
    app.component(key, component)
}


