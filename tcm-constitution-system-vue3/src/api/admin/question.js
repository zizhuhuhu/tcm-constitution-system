import instance from "@/axios";
//获取测评问题分页数据
export function getQuestionPageList(data) {
    return instance.post("/admin/question/list", data);
}
//删除测评问题数据
export function deleteQuestion(id) {
    return instance.post("/admin/question/delete", { id });
}
//发布测评问题
export function publishQuestion(data) {
    return instance.post("/admin/question/publish", data);
}
//获取测评问题详情
export function getQuestionDetail(id) {
    return instance.post("/admin/question/detail", { id });
}
//更新测评问题
export function updateQuestion(data) {
    return instance.post("/admin/question/update", data);
}

