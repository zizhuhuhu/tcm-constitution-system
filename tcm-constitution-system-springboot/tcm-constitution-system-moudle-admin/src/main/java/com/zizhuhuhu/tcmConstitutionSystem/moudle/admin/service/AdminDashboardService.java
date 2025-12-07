package com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.service;

import com.zizhuhuhu.tcmConstitutionSystem.module.common.utils.Response;

public interface AdminDashboardService {
    Response findDashboardStatistics();
    Response findDashboardPublishArticleStatistics();
    Response findDashboardPVStatics();
}
