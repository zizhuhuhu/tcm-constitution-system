import instance from "@/axios";
//获取文章分页数据
export function getArticlePageList(data) {
    return instance.post("/admin/article/list", data);
}
//删除文章数据
export function deleteArticle(id) {
    return instance.post("/admin/article/delete", { id });
}
//发布文章
export function publishArticle(data) {
    return instance.post("/admin/article/publish", data);
}
//获取文章详情
export function getArticleDetail(id) {
    return instance.post("/admin/article/detail", { id });
}
//更新文章
export function updateArticle(data) {
    return instance.post("/admin/article/update", data);
}