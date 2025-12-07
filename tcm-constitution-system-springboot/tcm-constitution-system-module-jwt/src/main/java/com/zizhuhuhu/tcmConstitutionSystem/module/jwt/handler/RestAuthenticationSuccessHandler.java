package com.zizhuhuhu.tcmConstitutionSystem.module.jwt.handler;

import com.zizhuhuhu.tcmConstitutionSystem.module.common.utils.Response;
import com.zizhuhuhu.tcmConstitutionSystem.module.jwt.JwtTokenHelper;
import com.zizhuhuhu.tcmConstitutionSystem.module.jwt.model.LoginRspVO;
import com.zizhuhuhu.tcmConstitutionSystem.module.jwt.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class RestAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        //从authentication对象中获取用户的UserDetails实例，这里是获取用户用户名
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        //通过用户名生成Token
        String username = userDetails.getUsername();
        String token = jwtTokenHelper.generateToken(username);
        //返回Token
        LoginRspVO loginRspVO = LoginRspVO.builder().token(token).build();
        ResultUtil.ok(response, Response.success(loginRspVO));
    }
}
