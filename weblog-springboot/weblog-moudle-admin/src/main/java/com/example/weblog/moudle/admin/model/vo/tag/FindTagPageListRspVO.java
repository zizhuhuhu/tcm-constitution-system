package com.example.weblog.moudle.admin.model.vo.tag;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("标签列表出参VO")
public class FindTagPageListRspVO {
    //标签ID
    private Long id;
    //标签名称
    private String name;
    //创建时间
    private LocalDateTime createTime;
}
