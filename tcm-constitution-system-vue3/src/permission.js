import router from '@/router/index'
import { getToken } from '@/composables/cookie'
import { showMessage } from '@/composables/util'
import { showPageLoading, hidePageLoading } from '@/composables/util'
import { useBlogSettingsStore } from '@/stores/blogsettings'
//全局路由前置守卫
router.beforeEach((to, from, next) => {
    console.log('==> 全局路由前置守卫')
    // 显示页面加载Loading
    showPageLoading()
    // 若用户想访问后台（以/admin 为前缀的路由）
    // 未登录，则强制跳转登录页面
    let token = getToken()
    if (!token && to.path.startsWith('/admin')) {
        showMessage('请先登录', 'warning')
        next({path: '/login'})
    } else if(token && to.path == '/login') {
        // 若用户已登录。且重复访问登录页面
        showMessage('您已登录，请勿重复登录', 'warning')
        //跳转到后台首页
        next({path: '/admin/index'})
    } else if(!to.path.startsWith('/admin')) {
        // 访问的不是后台路由,引入博客设置
        let blogSettingsStore = useBlogSettingsStore()
        //获取博客设置信息并保存到全局状态中
        blogSettingsStore.getBlogSettings()
        next()
    } else {
        next()
    }
})  

//全局路由后置守卫
router.afterEach((to, from) => {
    let title = (to.meta?.title || '') + ' - WebLog'
    document.title = title
    hidePageLoading() // 隐藏页面加载Loading
})