import instance from "@/axios";

//文章搜索
export function getArticleSearchPageList(data) {
    return instance.post("/article/search", data);
}