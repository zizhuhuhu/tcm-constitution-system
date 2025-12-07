package com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.category;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "查询文章详情入参")
public class FindCategoryDetailReqVO {
    @NotNull(message = "文章ID不能为空")
    private Long id;
}

