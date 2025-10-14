package com.example.weblog.module.common.exception;

import com.example.weblog.module.common.enums.ResponseCodeEnum;
import com.example.weblog.module.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.access.AccessDeniedException;
import java.util.Optional;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler({AccessDeniedException.class})
    public void throwAccessDeniedException(AccessDeniedException e) throws AccessDeniedException {
        //捕获到鉴权失败异常，主动抛出，交给RestAccessDeniedHandler去处理
        log.info("=======================捕获到AccessDeniedException");
        throw e;
    }

    @ExceptionHandler({BizException.class})
    @ResponseBody
    public Response<Object> handleBizException(HttpServletRequest request, BizException e){
        log.warn("{} request fail, errorCode: {}, errorMessage: {}",request.getRequestURI(), e.getErrorCode(), e.getErrMessage());
        return Response.fail(e);
    }
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Response<Object> handleOtherException(HttpServletRequest request, Exception e){
        log.error("{} request error, ", request.getRequestURI(), e);
        return Response.fail(ResponseCodeEnum.SYSTEM_ERROR);
    }
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public Response<Object> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e){
        //参数错误异常码
        String errorCode = ResponseCodeEnum.PARAM_NOT_VALID.getErrorCode();
        //获取BindingResult
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder sb = new StringBuilder();
        //获取校验不通过的字段，并组合错误信息 Email 邮箱格式不正确，当前值： ‘1234124qq.com’；
        Optional.ofNullable(bindingResult.getFieldErrors()).ifPresent(errors -> {
            errors.forEach(error->
                    sb.append(error.getField())
                    .append(" ")
                    .append(error.getDefaultMessage())
                    .append(", 当前值：'")
                    .append(error.getRejectedValue())
                    .append("';"));
        });
        //错误信息
        String errorMessage = sb.toString();
        log.warn("{} request error, errorCode: {}, errorMessage: {}", request.getRequestURI(), errorCode, errorMessage);
        return Response.fail(errorCode, errorMessage);

    }

}
