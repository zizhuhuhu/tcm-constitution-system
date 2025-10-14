package com.example.weblog.moudle.admin.controller;

import com.example.weblog.module.common.aspect.ApiOperationLog;
import com.example.weblog.module.common.utils.Response;
import com.example.weblog.moudle.admin.model.vo.artical.*;
import com.example.weblog.moudle.admin.service.AdminArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/article")
@Api(tags = "Admin文章模块")
public class AdminArticleController {
    @Autowired
    private AdminArticleService adminArticleService;

    @PostMapping("/publish")
    @ApiOperation(value = "文章发布")
    @ApiOperationLog(description = "文章发布")
    public Response publishArticle(@RequestBody @Validated PublishArticleReqVO publishArticleReqVO){
        return adminArticleService.publishArticle(publishArticleReqVO);
    }
    @PostMapping("/delete")
    @ApiOperation(value = "文章删除")
    @ApiOperationLog(description = "文章删除")
    public Response deleteArticle(@RequestBody @Validated DeleteArticleReqVO deleteArticleReqVO){
        return adminArticleService.deleteArticle(deleteArticleReqVO);
    }
    @PostMapping("/list")
    @ApiOperation(value = "查询文章分页数据")
    @ApiOperationLog(description = "查询文章分页数据")
    public Response findArticlePageList(@RequestBody @Validated FindArticlePageListReqVO findArticlePageListReqVO){
        return adminArticleService.findArticlePageList(findArticlePageListReqVO);
    }
    @PostMapping("/detail")
    @ApiOperation(value = "查询文章详情数据")
    @ApiOperationLog(description = "查询文章详情数据")
    public Response findArticleDetail(@RequestBody @Validated FindArticleDetailReqVO findArticleDetailReqVO){
        return adminArticleService.findArticleDetail(findArticleDetailReqVO);
    }
    @PostMapping("/update")
    @ApiOperation(value = "更新文章")
    @ApiOperationLog(description = "更新文章")
    public Response updateArticle(@RequestBody @Validated UpdateArticleReqVO updateArticleReqVO){
        return adminArticleService.updateArticle(updateArticleReqVO);
    }


}
