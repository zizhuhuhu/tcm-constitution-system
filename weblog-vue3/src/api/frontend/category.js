import instance from "@/axios";
//获取分类列表
export function getCategoryList(data) {
    return instance.post("/category/list", data);
}