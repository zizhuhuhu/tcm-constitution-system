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