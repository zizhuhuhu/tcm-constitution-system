package com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.dashboard;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ApiModel(value = "查询文章仪表盘PV访问量信息出参")
public class FindDashboardPVStatisticsInfoRspVO {
    //日期集合
    private List<String> pvDate;
    //pv浏览量集合
    private List<Long> pvCounts;
}
