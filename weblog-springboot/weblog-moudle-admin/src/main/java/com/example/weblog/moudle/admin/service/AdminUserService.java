package com.example.weblog.moudle.admin.service;

import com.example.weblog.module.common.utils.Response;
import com.example.weblog.moudle.admin.model.vo.UpdateAdminUserPasswordReqVO;

public interface AdminUserService {
    //修改密码
    Response updatePassword(UpdateAdminUserPasswordReqVO updateAdminUserPasswordReqVO);

    //获取当前登录用户信息
    Response findUserInfo();
}
