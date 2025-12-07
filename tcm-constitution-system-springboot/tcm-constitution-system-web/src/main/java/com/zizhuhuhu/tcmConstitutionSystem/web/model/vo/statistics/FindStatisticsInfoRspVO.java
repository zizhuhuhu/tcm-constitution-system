package com.zizhuhuhu.tcmConstitutionSystem.web.model.vo.statistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindStatisticsInfoRspVO {
    private Long articleTotalCount;
    private Long categoryTotalCount;
    private Long tagTotalCount;
    /**
     * 总浏览量
     */
    private Long pvTotalCount;
}
