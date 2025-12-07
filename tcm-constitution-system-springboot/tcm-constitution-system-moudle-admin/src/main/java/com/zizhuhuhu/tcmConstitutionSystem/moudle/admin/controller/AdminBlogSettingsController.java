package com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.controller;

import com.zizhuhuhu.tcmConstitutionSystem.module.common.aspect.ApiOperationLog;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.utils.Response;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.blogsettings.UpdateBlogSettingsReqVO;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.service.AdminBlogSettingsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/blog/setting")
@Api(tags = "Admin博客设置模块")
public class AdminBlogSettingsController {
    @Autowired
    private AdminBlogSettingsService adminBlogSettingsService;

    @PostMapping("/update")
    @ApiOperation(value = "博客基础信息修改")
    @ApiOperationLog(description = "博客基础信息修改")
    public Response updateBlogSettings(@RequestBody @Validated UpdateBlogSettingsReqVO updateBlogSettingsReqVO){
        return adminBlogSettingsService.updateBlogSettings(updateBlogSettingsReqVO);
    }
    @PostMapping("/detail")
    @ApiOperation(value = "博客详细信息获取")
    @ApiOperationLog(description = "博客详细信息获取")
    public Response findDetail(){
        return adminBlogSettingsService.findDetail();
    }

}
