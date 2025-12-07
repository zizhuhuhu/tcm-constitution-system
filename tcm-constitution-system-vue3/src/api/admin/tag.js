import instance from "@/axios";

//获取分类分页数据
export function getTagPageList(data) {

    return instance.post("/admin/tag/list", data);
}
export function addTag(data) {

    return instance.post("/admin/tag/add", data);
}
//删除分类
export function deleteTag(id) {
    return instance.post("/admin/tag/delete", {id})
}
//根据标签名模糊查询
export function searchTags(key) {
    return instance.post("/admin/tag/search", {key})
}
//获取标签下拉列表
export function getTagSelectList() {
    return instance.post("/admin/tag/select/list")
}