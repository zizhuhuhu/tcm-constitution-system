package com.example.weblog.web.controller;

import com.example.weblog.module.common.aspect.ApiOperationLog;
import com.example.weblog.module.common.utils.Response;
import com.example.weblog.web.service.CategoryService;
import com.example.weblog.web.service.StatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
@Api(tags = "统计信息")
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;
    @PostMapping("/info")
    @ApiOperation(value = "前台获取统计信息")
    @ApiOperationLog(description = "前台获取统计信息")
    public Response findCategoryList(){
        return statisticsService.findInfo();
    }
}

