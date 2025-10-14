<template>
 <div class="grid grid-cols-2 h-screen">
    <div class="col-span-2 order-2 p-10 md:col-span-1 md:order-1 bg-black">
        <!--flex-col用于指定子元素垂直排列-->
        <div class="flex justify-center items-center h-full flex-col animate__animated animate__bounceInDown">
            <h2 class="font-bold text-4xl mb-7 text-white">Weblog 博客登录</h2>
            <p class="text-white">一款由Spring Boot + Mybatis Plus + Vue 3.2 + Vite 4 开发的前后端分离博客。</p>
            <!--指定图片宽度为父级元素的1/2-->
            <img src="@/assets/developer.png" class="w-1/2">
        </div>
    </div>
    <div class="col-span-2 order-1 md:col-span-1 md:order-2 bg-white">
        <div class="flex justify-center items-center h-full flex-col animate__animated animate__bounceInUp">
            <h1 class="font-bold text-4xl mb-5">欢迎回来</h1>
            <div class="flex items-center justify-center mb-7 text-gray-400 space-x-2">
                <span class="h-[1px] w-16 bg-gray-200"></span>
                <span> 账号密码登录</span>
                <span class="h-[1px] w-16 bg-gray-200"></span>
            </div>
            <el-form class="w-5/6 md:w-2/5" ref="formRef" :rules="rules" :model="form">
                <el-form-item prop="username">
                    <!--输入框组件-->
                    <el-input size="large" v-model="form.username" placeholder="请输入用户名" :prefix-icon="'User'" clearable/>
                </el-form-item >
                <el-form-item prop="password">
                    <!--密码框组件-->
                    <el-input size="large" v-model="form.password" type="password" placeholder="请输入密码" :prefix-icon="'Lock'" clearable show-password/>
                </el-form-item>
                <el-form-item>
                    <!--登录按钮，宽度为100%-->
                    <el-button class="w-full" size="large" type="primary" @click="onSubmit" :loading="loading">登录</el-button>
                </el-form-item>
            </el-form>
        </div>
       
    </div>
 </div>
</template>

<script setup>
import {login} from '@/api/admin/user'
import {ref, reactive, onMounted, onBeforeUnmount } from 'vue';
import router from '@/router';
import { showMessage } from '@/composables/util';
import { setToken } from '@/composables/cookie';
import { useUserStore } from '@/stores/user';

//登录按钮加载状态
const loading = ref(false);


//表单引用
const formRef = ref(null)
//表单验证规则
const rules = {
    username: [
        {
            required: true,
            message: '用户名不能为空',
            trigger: 'blur'
        },
    ],
    password:[
        {
            required: true,
            message: '密码不能为空',
            trigger: 'blur'
        }
    ]
    
}
const form = reactive({
    username: '',
    password: ''
})
const userStore = useUserStore()
//登录
const onSubmit = () => {
    console.log('登录')
    //先验证form表单字段
    formRef.value.validate((valid) => {
        if(!valid){
            console.log('表单验证不通过')
            return false
        }
        loading.value = true; //设置按钮加载状态
    //调用登录接口
    login(form.username, form.password).then((res) => {
        console.log(res)
        //判断是否成功
        if (res.success == true){ //data是从后端返回的数据
            let token = res.data.token //获取token
            //设置token到本地存储
            setToken(token)
            //获取用户信息，并存储到全局状态中
            userStore.setUserInfo()
            //提示消息
            showMessage('登录成功')
            //跳转到后台首页
            router.push('/admin/index')
        } else{
            //获取服务端返回的错误消息
            let message = res.message
            //提示消息
           showMessage(message, 'error')
        }
    }).finally(() => {
        loading.value = false; //重置按钮加载状态
    })
})
}
//按回车键后，执行登录操作
function onKeyUp(e) {
    console.log(e)
    if (e.key === 'Enter') {
        onSubmit();
    }
}
//添加键盘监听
onMounted(() => {
    console.log('添加键盘监听')
    document.addEventListener('keyup', onKeyUp)
})
//移除键盘监听
onBeforeUnmount(() => {
    document.removeEventListener('keyup', onKeyUp)
})
</script>