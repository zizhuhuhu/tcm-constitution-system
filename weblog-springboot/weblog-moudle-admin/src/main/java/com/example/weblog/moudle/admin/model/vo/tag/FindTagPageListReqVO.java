package com.example.weblog.moudle.admin.model.vo.tag;

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
@ApiModel(value = "查询标签数据入参 VO")
public class FindTagPageListReqVO extends BasePageQuery{
    //标签名称
    private String name;
    //创建的起始日期
    private LocalDate startDate;
    //创建的结束日期
    private LocalDate endDate;
}
