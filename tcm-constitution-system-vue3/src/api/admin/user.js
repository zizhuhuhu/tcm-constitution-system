import instance from "@/axios";
//登录接口
export function login(username, password){
    return instance.post("login", {username, password});
}
//获取登录用户信息
export function getUserInfo(){
    return instance.post("admin/user/info");
}
//修改用户密码
export function updateAdminPassword(data) {
    return instance.post("admin/password/update", data)
}