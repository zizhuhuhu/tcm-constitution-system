package com.zizhuhuhu.tcmConstitutionSystem.web.controller;

import com.zizhuhuhu.tcmConstitutionSystem.module.common.aspect.ApiOperationLog;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.utils.Response;
import com.zizhuhuhu.tcmConstitutionSystem.web.service.BlogSettingsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blog/settings")
@Api(tags = "博客设置")
public class BlogSettingController {
    @Autowired
    private BlogSettingsService blogSettingsService;
    @PostMapping("/detail")
    @ApiOperation(value = "前台获取博客详情")
    @ApiOperationLog(description = "前台获取博客详情" )
    public Response findDetail() {
        return blogSettingsService.findDetail();
    }
}
