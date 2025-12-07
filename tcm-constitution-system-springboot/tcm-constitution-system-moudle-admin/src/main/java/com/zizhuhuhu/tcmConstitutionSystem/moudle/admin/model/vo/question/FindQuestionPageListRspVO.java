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
public class FindQuestionPageListRspVO {
    //问题id
    private Long id;
    //问题题目
    private String title;
    //问题类型
    private String questionType;
    //问题分类
    private String constitutionCategoryName;
    //问题顺序
    private Integer sortOrder;
    //发布时间
    private LocalDateTime createTime;

}
