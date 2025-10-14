package com.example.weblog.moudle.admin.model.vo.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadFileRepVO {
    //文件访问链接
    private String url;
}
