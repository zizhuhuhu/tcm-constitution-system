import { useMenuStore } from '@/stores/menu'
import { ref } from 'vue'
import { setTagList, getTagList } from '@/composables/cookie'
import route from '@/router'
import { useRoute, useRouter, onBeforeRouteUpdate } from 'vue-router'

export function useTabList() {
    const menuStore = useMenuStore()
const router = useRouter()

const handleCloseTab = (command) => {
    //首页路由
    let indexPath = '/admin/index'
    //如果是关闭其他
    if (command === 'closeOthers') {
        //过滤掉当前被选中的tab和首页tab
        tabList.value = tabList.value.filter((tab) => tab.path == activeTab.value || tab.path == indexPath)
    } else if (command === 'closeAll') {
        //如果是关闭全部，则只保留首页tab
        //切换回首页
        activeTab.value = indexPath
        //只保留首页tab
        tabList.value = tabList.value.filter((tab) => tab.path == indexPath)
        //切换标签页
        tabChange(activeTab.value)
    }
    //存储到cookie中
    setTagList(tabList.value)
}

//当前被选中的tab
const  activeTab = ref(route.path)
//导航栏tab数组
const tabList = ref([
    {
        title: '仪表盘',
        path: "/admin/index"
    },
])

//添加Tab标签页
function addTab(tab) {
    //标签是否不存在
    let isTabNotExisted = tabList.value.findIndex(item => item.path == tab.path) == -1
    //要是不存在
    if(isTabNotExisted){
        //添加标签
        tabList.value.push(tab)
    }
    //存储tabList到cookie中
    setTagList(tabList.value)
}
function initTabList() {
    //获取cookie中缓存起来的标签导航栏数据
    let tabs = getTagList()
    //如果存在
    if(tabs){
        tabList.value = tabs
    } 
}
initTabList();
//在切换路由前被调用
onBeforeRouteUpdate((to, from) => {
    //设置被激活的Tab标签
    activeTab.value = to.path
    //添加Tab标签
    addTab({
        title: to.meta.title,
        path: to.path
    })
    //打印日志
    console.log({
        title: to.meta.title,
        path: to.path
    })
})
//标签页切换事件
const tabChange = (path) => {
    activeTab.value = path
    //切换路由
    router.push(path)
}
const removeTab = (path) => {
    let tabs = tabList.value
    //当前被选中的tab
    let actTab = activeTab.value
    //如果当前被选中的tab是要删除的tab,则需要判断其被删除后，需要激活哪个tab
    if (actTab == path) {
        //循环tabList
        tabs.forEach((tab, index) => {
            //取得被选中的tab元素
            if(tab.path == path){
                //得到被选中的标签页下标，如果它后面还有标签页，则取下一个标签页，否则取上一个
                let nextTab = tabs[index + 1] || tabs[index - 1]
                if(nextTab){
                    actTab = nextTab.path
                }
            }
        })
    }
    //需要被重新激活的标签页
    activeTab.value = actTab
    //过滤掉被删除的tab
    tabList.value = tabList.value.filter((tab) => tab.path != path)
    //存储到cookie中
    setTagList(tabList.value)
    //切换标签页
    tabChange(activeTab.value)
    }
    return {
        activeTab,
        tabList,
        handleCloseTab,
        initTabList,
        tabChange,
        removeTab,
        menuStore,
    }
}
