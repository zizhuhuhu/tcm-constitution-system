package com.example.weblog.moudle.admin.service;

import com.example.weblog.module.common.utils.Response;

public interface AdminDashboardService {
    Response findDashboardStatistics();
    Response findDashboardPublishArticleStatistics();
    Response findDashboardPVStatics();
}
