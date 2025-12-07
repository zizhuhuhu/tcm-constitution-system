package com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.question;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "查询问题详情出参")
public class FindQuestionDetailRspVO {
    private Long id;
    private String title;
    private String description;
    private Integer sortOrder;
    private String questionType;
    private Long constitutionCategoryId;
    private Integer isRequired;
}
