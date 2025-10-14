package com.example.weblog.web.controller;

import com.example.weblog.module.common.aspect.ApiOperationLog;
import com.example.weblog.module.common.utils.Response;
import com.example.weblog.web.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tag")
@Api(tags = "标签")
public class TagController {
    @Autowired
    private TagService tagService;
    @PostMapping("/list")
    @ApiOperationLog(description = "前台获取标签列表")
    @ApiOperation(value = "前台获取标签列表")
    public Response findTagList() {
        return tagService.findTagList();
    }
}
