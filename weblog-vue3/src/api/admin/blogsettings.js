import instance from "@/axios";


// 获取博客设置详情
export function getBlogSettingsDetail() {
    return instance.post("/admin/blog/setting/detail")
}

// 更新博客设置
export function updateBlogSettings(data) {
    return instance.post("/admin/blog/setting/update", data)
}