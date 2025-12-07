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
    //分类描述
    private String description;
    //分类特征
    private String characteristics;
    //创建时间
    private LocalDateTime createTime;
}
