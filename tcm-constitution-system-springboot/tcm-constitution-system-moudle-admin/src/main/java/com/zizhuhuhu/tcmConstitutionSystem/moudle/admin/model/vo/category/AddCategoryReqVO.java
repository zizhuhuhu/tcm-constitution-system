package com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.category;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "添加分类VO")
public class AddCategoryReqVO {
    @NotBlank(message = "分类名称不能为空")
    @Length(min = 1, max = 10, message = "分类名称字数限制1~10之间")
    private String name;
    @NotBlank(message = "分类描述不能为空")
    private String description;
    @NotBlank(message = "分类特征不能为空")
    private String characteristics;
    @NotBlank(message = "分类常见症状不能为空")
    private String commonSymptoms;
    @NotBlank(message = "分类心理特点不能为空")
    private String psychologicalTraits;
    private String dietAdvice;
    private String lifestyleAdvice;
    private String exerciseAdvice;
    private String acupointAdvice;
}
