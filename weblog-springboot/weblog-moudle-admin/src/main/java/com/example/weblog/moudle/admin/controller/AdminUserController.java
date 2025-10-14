package com.example.weblog.moudle.admin.controller;

import com.example.weblog.module.common.aspect.ApiOperationLog;
import com.example.weblog.module.common.utils.Response;
import com.example.weblog.moudle.admin.model.vo.UpdateAdminUserPasswordReqVO;
import com.example.weblog.moudle.admin.service.AdminUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Api(tags = "Admin用户模块")
public class AdminUserController {
    @Autowired
    private AdminUserService userService;
    @PostMapping("/password/update")
    @ApiOperation(value = "修改用户密码")
    @ApiOperationLog(description = "修改用户密码")
    public Response updatePassword(@RequestBody @Validated UpdateAdminUserPasswordReqVO updateAdminUserPasswordReqVO){
        return userService.updatePassword(updateAdminUserPasswordReqVO);
    }

    @PostMapping("/user/info")
    @ApiOperation(value = "获取用户信息")
    @ApiOperationLog(description = "获取用户信息")
    public Response findUserInfo(){
        return userService.findUserInfo();
    }
}
