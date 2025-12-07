package com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.question;

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
@ApiModel(value = "查询问题详情入参")
public class FindQuestionDetailReqVO {
    @NotNull(message = "文章ID不能为空")
    private Long id;
}
