
<template>
    <el-affix :offset="0">
        <div class="bg-white h-[64px] pr-4 border-b border-slate-200 flex border-slate-100">
        <!--左边栏收缩、展开-->
        <div class="w-[42px] h-[64px] cursor-pointer flex items-center justify-center text-gary-700 hover:bg-gray-200" @click="handleMenuWidth">
            <el-icon>
                <Fold v-if="menuStore.menuWidth === '250px'"/>
                <Expand v-else/>
            </el-icon>
        </div>
        
        <!--右边容器，通过ml-auto让其在父容器右边-->
        <div class="ml-auto flex">
            <el-tooltip class="box-item" effect="dark" content="刷新" placement="bottom">
                <div class="w-[42px] h-[64px] cursor-pointer flex items-center justify-center text-gary-700 mr-2  hover:bg-gray-200" @click="handleRefresh">
                    <el-icon>
                        <Refresh />
                    </el-icon>
                </div>
            </el-tooltip>
             <!-- 点击跳转前台首页 -->
                <el-tooltip class="box-item" effect="dark" content="跳转前台" placement="bottom">
                    <div class="w-[42px] h-[64px] cursor-pointer flex items-center justify-center text-gray-700 hover:bg-gray-200"
                        @click="router.push('/')">
                        <el-icon>
                            <Monitor />
                        </el-icon>
                    </div>
                </el-tooltip>
            <!--点击全屏展示-->
            <el-tooltip class="box-item" effect="dark" content="全屏展示" placement="bottom">
                <div class="w-[42px] h-[64px] cursor-pointer flex items-center justify-center text-gary-700 mr-2  hover:bg-gray-200" @click="toggle">
                    <el-icon>
                        <FullScreen v-if="!isFullScreen"/>
                        <Aim v-else />
                    </el-icon>
                </div>
            </el-tooltip>
            
            
            <!--登录用户头像-->
            <el-dropdown class="flex items-center justify-center" @command="handleCommand">
                <span class="el-dropdown-link flex items-center justify-center text-gary-700 text-xs">
                    <!--头像-->
                    <el-avatar class="mr-2" :size="25" src="https://img.quanxiaoha.com/quanxiaoha/f97361c0429d4bb1bc276ab835843065.jpg"/>
                    {{ userStore.userInfo.username }}
                    <el-icon class="el-icon--right">
                        <arrow-down />
                    </el-icon>
                </span>
                <template #dropdown>
                    <el-dropdown-menu>
                        <el-dropdown-item command="updatePassword">修改密码</el-dropdown-item>
                        <el-dropdown-item command="logout">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </template>
            </el-dropdown>

            <!-- 修改密码 -->
   <el-dialog v-model="dialogVisible" title="修改密码" width="40%" :draggable ="true" :close-on-click-modal="false" :close-on-press-escape="false">
        <el-form ref="formRef" :rules="rules" :model="form">
                    <el-form-item label="用户名" prop="username" label-width="120px">
                        <!-- 输入框组件 -->
                        <el-input size="large" v-model="form.username" placeholder="请输入用户名" clearable disabled />
                    </el-form-item>
                    <el-form-item label="密码" prop="password" label-width="120px">
                        <el-input size="large" type="password" v-model="form.password" placeholder="请输入密码"
                             clearable show-password />
                    </el-form-item>
                    <el-form-item label="确认密码" prop="rePassword" label-width="120px">
                        <el-input size="large" type="password" v-model="form.rePassword" placeholder="请确认密码"
                             clearable show-password />
                    </el-form-item>
                </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="onSubmit">
                    提交
                </el-button>
            </span>
        </template>
    </el-dialog>
        </div>
    </div>
    </el-affix>
    <!--通过flex指定水平布局-->
    
</template>
<script setup>

//引入用户Store
import { useUserStore } from '@/stores/user';
import { useRouter } from 'vue-router';
//引入useFullScreen
import { useFullscreen } from '@vueuse/core';
import { useMenuStore } from '@/stores/menu';
import { showMessage, showModel } from '@/composables/util';
import { ref, reactive, watch } from 'vue';
import { updateAdminPassword } from '@/api/admin/user';
//对话框是否提示
const dialogVisible = ref(false)
const formRef = ref(null)
//引入用户Store
const userStore = useUserStore();
//监听Pinia store中某个值的变化
watch(() => userStore.userInfo.username, (newValue, oldValue) => {
    //在这里处理变化后的值
    console.log('新值：', newValue);
    console.log('旧值：', oldValue);
    //重新将新的值设置到form对象中
    form.username = newValue
});
// 修改用户密码表单对象
    const form = reactive({
        username: userStore.userInfo.username || '',
        password: '',
        rePassword: ''
    })

    // 规则校验
    const rules = {
        username: [
            {
                required: true,
                message: '用户名不能为空',
                trigger: 'blur'
            }
        ],
        password: [
            {
                required: true,
                message: '密码不能为空',
                trigger: 'blur',
            },
        ],
        rePassword: [
            {
                required: true,
                message: '确认密码不能为空',
                trigger: 'blur',
            },
        ]
    }

const router = useRouter()
// isFullScreen用于判断是否全屏, toggle用于动态切换全屏状态
const { isFullScreen, toggle } = useFullscreen();
//刷新页面
const handleRefresh = () => location.reload()
//引入菜单store
const menuStore = useMenuStore();
//icon点击事件
const handleMenuWidth = () => {
    //动态设置菜单宽度大小
    menuStore.handleMenuWidth();
};
const handleCommand = (command) => {
    //更新密码
    if(command == 'updatePassword'){
        //显示修改密码对话框
        dialogVisible.value = true
    } else if(command == 'logout'){ 
        console.log('点击退出登录')
        //退出登录
        logout()
    }
}
function logout() {
    console.log("弹窗")
    showModel('是否确认要退出登录？').then(() => {
        userStore.logout()
        showMessage('退出登录成功！')
        console.log('已退出登录')
        //跳转登录页
        router.push('/login')
    })
}
const onSubmit = () => {
                    // 先验证 form 表单字段
                    formRef.value.validate((valid) => {
                        if (!valid) {
                            console.log('表单验证不通过')
                            return false
                        }

                        if (form.password != form.rePassword) {
                            showMessage('两次密码输入不一致，请检查！', 'warning')
                            return
                        }

                        // 调用修改用户密码接口
                        updateAdminPassword(form).then((res) => {
                            console.log(res)
                            // 判断是否成功
                            if (res.success == true) {
                                showMessage('密码重置成功，请重新登录！')
                                // 退出登录
                                userStore.logout()

                                // 隐藏对话框
                                dialogVisible.value = false

                                // 跳转登录页
                                router.push('/login')
                            } else {
                                // 获取服务端返回的错误消息
                                let message = res.message
                                // 提示消息
                                showMessage(message, 'error')
                            }
                        })
                    })
                }

</script>