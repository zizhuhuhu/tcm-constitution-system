package com.example.weblog.moudle.admin.service.impl;

import com.example.weblog.module.common.domain.mapper.UserMapper;
import com.example.weblog.module.common.enums.ResponseCodeEnum;
import com.example.weblog.module.common.utils.Response;
import com.example.weblog.moudle.admin.model.vo.UpdateAdminUserPasswordReqVO;
import com.example.weblog.moudle.admin.model.vo.user.FindUserInfoRspVO;
import com.example.weblog.moudle.admin.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Response updatePassword(UpdateAdminUserPasswordReqVO updateAdminUserPasswordReqVO) {
        //拿到用户名、密码
        String username = updateAdminUserPasswordReqVO.getUsername();
        String password = updateAdminUserPasswordReqVO.getPassword();
        //加密密码
        String encodePassword = passwordEncoder.encode(password);
        //更新到数据库
        int count = userMapper.updatePasswordByUsername(username, encodePassword);
        return count == 1 ? Response.success() : Response.fail(ResponseCodeEnum.USERNAME_NOT_FOUND);
    }

    @Override
    public Response findUserInfo() {
        //获取存储在ThreadLocal中的用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //拿到用户名
        String username = authentication.getName();
        return Response.success(FindUserInfoRspVO.builder().username(username).build());
    }
    //修改密码


}
