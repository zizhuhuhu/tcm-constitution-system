package com.example.weblog.moudle.admin.model.vo.question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PublishQuestionReqVO {
    @NotBlank(message = "问题标题不能为空")
    private String title;
    private String description;
    @NotNull(message = "问题排序不能为空")
    private Integer sortOrder;
    @NotNull(message = "问题类型不能为空")
    private Integer questionType;
    @NotNull(message = "体质类别不能为空")
    private Long constitutionCategoryId;
    @NotNull(message = "问题是否必填不能为空")
    private Integer isRequired;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer deleted;

}
