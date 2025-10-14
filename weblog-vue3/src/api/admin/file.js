import instance from "@/axios";
//上传文件
export function uploadFile(form) {
    return instance.post("/admin/file/upload", form)
}