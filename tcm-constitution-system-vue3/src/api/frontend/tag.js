import instance from "@/axios";
//获取标签列表
export function getTagList(data) {
    return instance.post("/tag/list", data);
}