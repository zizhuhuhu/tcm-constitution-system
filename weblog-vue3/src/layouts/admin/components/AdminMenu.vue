<template>
    <div class="fixed overflow-y-auto bg-slate-800 h-screen text-white menu-container transition-all" :style="{ width: menuStore.menuWidth }">
        <!--顶部Logo,指定高度为64px,和左边的Header头保持一样高-->
        <div class="flex items-center justify-center h-[64px]">
            <img v-if="menuStore.menuWidth == '250px'" src="@/assets/weblogLogo.jpg" class="h-[60px]">
            <img v-else src="@/assets/weblog-mini-logo.png" class="h-[60px]">
        </div>
        <!--下方菜单-->
        <el-menu :default-active="defaultActive" @select="handleSelect" :collapse="isCollapse" :collapse-transition="false">
            <template v-for="(item, index) in menus" :key="index">
                <el-menu-item :index="item.path">
                    <el-icon>
                        <!--动态图标-->
                        <component :is="item.icon"></component>
                    </el-icon>
                    <span>{{ item.name }}</span>
                </el-menu-item>
            </template>
      </el-menu>
    </div>
</template>
<style>
    .el-menu {
        background-color: rgb(30 41 59 / 1);
        border-right: 0;
    }
    .el-sub-menu__title {
        color: #fff;
    }
    .el-sub-menu_title:hover {
        background-color: #ffffff10;
    }
    .el-sub-item.is-active {
        background-color: var(--el-color-primary);
        color: #fff;
    }
    .el-menu-item {
        color: #fff;
    }
    .el-menu-item:hover {
        background-color: #ffffff10;
    }
</style>
<script setup>
import AdminArticalList from '@/pages/admin/article-list.vue'
import AdminBlogSetting from '@/pages/admin/blog-setting.vue'
import AdminCategoryList from '@/pages/admin/category-list.vue'
import AdminTagList from '@/pages/admin/tag-list.vue'
import weblogLogo from '@/assets/weblogLogo.jpg'
import {ref, computed} from 'vue'
import router from '@/router/index.js'
import { useRoute } from 'vue-router'
import { useMenuStore } from '@/stores/menu.js'
//引入useMenuStore
const menuStore = useMenuStore();
//是否折叠
const isCollapse = computed(() => !(menuStore.menuWidth === '250px'));
const route = useRoute();
//根据路由地址判断哪个菜单被选中
const defaultActive = ref(route.path)

//菜单选择事件
const handleSelect = (path) => {
    router.push(path)
}

const menus = [
    {
        'name': '仪表盘',
        'icon': 'Monitor',
        'path': '/admin/index'
    },
    {
        'name': '文章管理',
        'icon': 'Document',
        'path': '/admin/artical/list'
    },
    {
        'name': '分类管理',
        'icon': 'FolderOpened',
        'path': '/admin/category/list',
    },
    {
        'name': '标签管理',
        'icon': 'PriceTag',
        'path': '/admin/tag/list'
    },
    {
        'name': '博客设置',
        'icon': 'Setting',
        'path': '/admin/blog/setting'
    },
    
]
</script> 

