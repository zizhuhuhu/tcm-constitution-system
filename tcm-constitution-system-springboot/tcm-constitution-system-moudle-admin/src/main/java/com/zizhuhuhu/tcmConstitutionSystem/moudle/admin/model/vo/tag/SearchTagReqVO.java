package com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.tag;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "模糊查询标签VO")
public class SearchTagReqVO {
    @NotNull(message = "模糊查询不能为空")
    private String key;
}
