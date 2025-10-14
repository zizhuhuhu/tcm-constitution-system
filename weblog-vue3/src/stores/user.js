import {defineStore} from 'pinia'
import {ref} from 'vue'
import { getUserInfo } from '@/api/admin/user'
import { removeToken } from '@/composables/cookie'
export const useUserStore = defineStore('user', () => {
    //用户信息
    const userInfo = ref({})
    //设置用户信息
    function setUserInfo(){
        //调用后获取用户信息接口
        getUserInfo().then(res => {
            console.log("查询显示用户信息API返回数据：", res)
            if (res.success == true) {
                console.log('用户名:' + res.data)
                userInfo.value = res.data
            }
        })
    }
    function logout() {
        //删除cookie中的token令牌
        removeToken()
        //删除登录用户信息
        userInfo.value = {}

    }

    return {userInfo, setUserInfo, logout}
},
{
    //开启持久化
    persist: true,
})