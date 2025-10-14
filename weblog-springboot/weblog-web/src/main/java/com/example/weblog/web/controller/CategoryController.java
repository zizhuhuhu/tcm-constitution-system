package com.example.weblog.web.controller;

import com.example.weblog.module.common.aspect.ApiOperationLog;
import com.example.weblog.module.common.utils.Response;
import com.example.weblog.web.service.CategoryService;
import com.fasterxml.classmate.members.ResolvedParameterizedMember;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@Api(tags = "分类")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/list")
    @ApiOperation(value = "前台获取分类列表")
    @ApiOperationLog(description = "前台获取分类列表")
    public Response findCategoryList(){
        return categoryService.findCategoryList();
    }
}
