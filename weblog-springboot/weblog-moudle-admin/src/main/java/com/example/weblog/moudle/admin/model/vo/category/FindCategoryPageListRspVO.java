package com.example.weblog.moudle.admin.model.vo.category;

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
@ApiModel("分类分页列表出参VO")
public class FindCategoryPageListRspVO {
    //分类ID
    private Long id;
    //分类名称
    private String name;
    //创建时间
    private LocalDateTime createTime;
}
