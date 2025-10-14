import Index from '@/pages/frontend/index.vue'
import { createRouter, createWebHashHistory, createWebHistory} from 'vue-router'
import login from '@/pages/admin/login.vue'
import AdminIndex from '@/pages/admin/index.vue'
import Admin from '@/layouts/admin/admin.vue'
import AdminArticalList from '@/pages/admin/article-list.vue'
import AdminBlogSetting from '@/pages/admin/blog-setting.vue'   
import AdminCategoryList from '@/pages/admin/category-list.vue'
import AdminTagList from '@/pages/admin/tag-list.vue'
import ArticleDetail from '@/pages/frontend/article-detail.vue'
import NotFound from '@/pages/frontend/404.vue'
//统一在这样声明所有路由

const routes = [
    {
        path: '/admin',
        component: Admin,
        //使用到admin.vue布局的，都需要放置在其子路由下面
        children: [
            {
                path: '/admin/index',
                component: AdminIndex,
                meta: {
                    title: 'Admin后台首页'
                }
            },
            {
                path: '/admin/artical/list',
                component: AdminArticalList,
                meta: {
                    title: '文章管理'
                }
            },
            {
                path: '/admin/blog/setting',
                component: AdminBlogSetting,    
                meta: {
                    title: '博客设置'
                }
            },
            {
                path: '/admin/category/list',
                component: AdminCategoryList,   
                meta: {
                    title: '分类管理'
                }
            },
            {
                path: '/admin/tag/list',    
                component: AdminTagList,
                meta: {
                    title: '标签管理'
                }
            }
        ]
    },
    {
        path: '/', //路由地址
        component: Index, //对应组件
        meta: {
            title: 'Weblog 首页' //页面标题
        }
    },
    {
        path: '/login',
        component: login,
        meta: {
            title: 'Weblog 登录页'
        }
    },
    {
        path: '/article/:articleId', //文章详情页
        component: ArticleDetail,
        meta: {
            title: 'Weblog 详情页'
        }
    },
    {
        path: '/:pathMatch(.*)*', // 404页面,必须放在最后面
        component: NotFound,
        name: 'NotFound',
        meta: {
            title: '404 页'
        }
    }
    
]
//创建路由
const router = createRouter({
    //指定路由的历史管理方式，hash:URL路径通过hash符号（#）进行标识
    history: createWebHashHistory(),
    routes
})
//ES6模块导出语句，它用于将router对象导出，以便其他文件可以导入和使用这个对象
export default router