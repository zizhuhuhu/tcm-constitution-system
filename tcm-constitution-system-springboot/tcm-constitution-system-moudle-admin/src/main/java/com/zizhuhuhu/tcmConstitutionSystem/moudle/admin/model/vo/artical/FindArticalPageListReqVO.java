package com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.artical;

import com.zizhuhuhu.tcmConstitutionSystem.module.common.model.BasePageQuery;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "查看文章分页VO")
public class FindArticalPageListReqVO extends BasePageQuery {
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
}
