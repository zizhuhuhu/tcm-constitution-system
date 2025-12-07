import instance from "@/axios";

//获取分类分页数据
export function getCategoryPageList(data) {

    return instance.post("/admin/category/list", data);
}
export function addCategory(data) {

    return instance.post("/admin/category/add", data);
}
//删除分类
export function deleteCategory(id) {
    return instance.post("/admin/category/delete", {id})
}
export function getCategorySelectList() {
    return instance.post("/admin/category/select/list")
}
//更新分类
export function updateCategory(data) {
    return instance.post("/admin/category/update", data)
}
//获取分类详情
export function getCategoryDetail(id) {
    return instance.post("/admin/category/detail", {id})
}
