package com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.category;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "查询分类详情出参")
public class FindCategoryDetailRspVO {
    //分类id
    private Long id;
    private String name;
    private String description;
    private String characteristics;
    private String commonSymptoms;
    private String psychologicalTraits;
    private String dietAdvice;
    private String lifestyleAdvice;
    private String exerciseAdvice;
    private String acupointAdvice;
}
