package com.example.weblog.module.jwt.handler;

import com.example.weblog.module.common.enums.ResponseCodeEnum;
import com.example.weblog.module.common.utils.Response;
import com.example.weblog.module.jwt.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.warn("登录成功访问受保护的资源，但权限不够：", accessDeniedException);
        //预留，后面引入多角色时会用到
        ResultUtil.fail(response, Response.fail(ResponseCodeEnum.FORBIDDEN));
    }
}
