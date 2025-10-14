package com.example.weblog.moudle.admin.model.vo.category;

import com.example.weblog.module.common.model.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "查询分类分页数据入参 VO")
public class FindCategoryPageListReqVO extends BasePageQuery {
    //分类名称
    private String name;
    //创建的起始日期
    private LocalDate startDate;
    //创建的结束日期
    private LocalDate endDate;
}
