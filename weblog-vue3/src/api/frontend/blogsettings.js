import instance from "@/axios";

//获取博客设置详情
export function getBlogSettingsDetail() {
    return instance.post("/blog/settings/detail");
}