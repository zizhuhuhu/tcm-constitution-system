import { useCookies } from "@vueuse/integrations/useCookies";
const cookie = useCookies();
//=====================================Token========================================
//存储在Cookie中的Token的key
const TOKEN_KEY = 'Authorization';
//获取Token
export function getToken() {
  return cookie.get(TOKEN_KEY);
}
//设置token到Cookies中
export function setToken(token) {
    return  cookie.set(TOKEN_KEY, token)
}
//删除Cookies中的token
export function removeToken() {
    return cookie.remove(TOKEN_KEY);
}

//=====================================标签页========================================
//存储在Cookie中的标签页数据的key
const TAG_LIST_KEY = 'tagList';
//获取TabList
export function getTagList() {
    return cookie.get(TAG_LIST_KEY);
}
//存储TabList到Cookie
export function setTagList(tagList) {
    return cookie.set(TAG_LIST_KEY, tagList)
}