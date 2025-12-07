import axios from "axios";
import { getToken, removeToken } from "@/composables/cookie";
import { showMessage } from "./composables/util";
import { useUserStore } from "@/stores/user";
//创建Axios实例
const instance = axios.create({
    baseURL: "/api", //API基础URL
    timeout: 7000 //请求超时时间
})
//添加请求拦截器
instance.interceptors.request.use(function (config) {
    //在发送请求之前做些什么
    const token = getToken(); //获取token
    console.log('统一添加请求头中的Token:' + token);
    //当token存在时，添加到请求头中
    if (token) {
        config.headers['Authorization'] = 'Bearer ' + token; //设置请求头中的Authorization字段
    }
    return config;
},function (error) {
    //请求错误时做些什么
    return Promise.reject(error)
});
//添加响应拦截器
instance.interceptors.response.use(function (response) {
    //2xx范围内的状态码都会触发该函数
    //对响应数据做点什么
    return response.data
}, function (error) {
    //超出2xx范围的状态码都会触发该函数
    //对响应错误做点什么
    let status = error.response.status; //获取错误状态码
    //如果是401未授权错误，清除token
    if (status === 401) {
        showMessage('登录已过期，请重新登录', 'warning');
       
        //退出登录
        let userStore = useUserStore();
        userStore.logout();
        //刷新页面
        location.reload();
    }
    //后台有错误提示就用提示文字，默认提示为‘请求失败’
    let errorMsg = error.response.data.message || '请求失败';
    //弹窗提示
    showMessage(errorMsg, 'error');
    return Promise.reject(error);
});
//暴露出去
export default instance;