package com.example.weblog.moudle.admin.controller;

import com.example.weblog.module.common.aspect.ApiOperationLog;
import com.example.weblog.module.common.utils.Response;
import com.example.weblog.moudle.admin.service.AdminDashboardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Admin 仪表盘")
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {
    @Autowired
    private AdminDashboardService adminDashboardService;
    @PostMapping("/statistics")
    @ApiOperation(value = "获取后台仪表盘基础统计信息")
    @ApiOperationLog(description = "获取后台仪表盘基础统计信息")
    public Response findDashboardStatistics() {
        return adminDashboardService.findDashboardStatistics();
    }
    @PostMapping("/publishArticle/statistics")
    @ApiOperation(value = "获取后台仪表盘文章发布热点统计信息")
    @ApiOperationLog(description = "获取后台仪表盘文章发布热点统计信息")
    public Response findDashboardPublishArticleStatistics() {
        return adminDashboardService.findDashboardPublishArticleStatistics();
    }
    @PostMapping("/pv/statistics")
    @ApiOperation(value = "获取后台仪表盘最近一周PV访问量信息")
    @ApiOperationLog(description = "获取后台仪表盘最近一周PV访问量信息")
    public Response findDashboardPVStatistics() {
        return adminDashboardService.findDashboardPVStatics();
    }

}
