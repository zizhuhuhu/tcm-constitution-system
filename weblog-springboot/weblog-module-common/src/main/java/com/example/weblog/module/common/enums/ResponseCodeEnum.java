package com.example.weblog.module.common.enums;

import com.example.weblog.module.common.exception.BaseExceptionInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCodeEnum implements BaseExceptionInterface {
    SYSTEM_ERROR("10000", "出错啦，后台小哥正在努力修复中……"),
    PRODUCT_NOT_FOUND("20000", "该产品不存在（测试使用"),
    PARAM_NOT_VALID("10001", "参数错误"),
    LOGIN_FAIL("2000", "登录失败"),
    USERNAME_OR_PWD_ERROR("20001", "用户名或密码错误"),
    UNAUTHORIZED("20002", "无访问权限，请先登录！"),
    FORBIDDEN("20004", "演示账号仅支持查询操作"),
    USERNAME_NOT_FOUND("20003", "该用户不存在"),
    CATEGORY_NAME_IS_EXISTED("20005", "该分类已存在，请勿重复添加"),
    TAG_CANT_DUPLICATE("20006", "请勿添加表中已存在的标签！"),
    TAG_NOT_FOUND("20007", "该标签不存在"),
    FILE_UPLOAD_FAILED("20008", "文件上传失败！"),
    CATEGORY_NOT_EXISTED("20009", "提交的分类不存在！"),
    ARTICLE_NOT_FOUND("20010", "该文章不存在！"),
    CATEGORY_CAN_NOT_DELETE("20011", "该分类下包含文章，请先删除对应文章，才能删除！"),
    TAG_CAN_NOT_DELETE("20012", "该标签下包含文章，请先删除对应文章，才能删除！");

    private String errorCode;
    private String errorMessage;
}
