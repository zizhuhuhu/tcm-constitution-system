import instance from "@/axios";
export function getArticlePageList(data) {
    return instance.post("/article/list", data);
}
//获取文章详情
export function getArticleDetail(articleId) {
    return instance.post("/article/detail", {articleId});
}