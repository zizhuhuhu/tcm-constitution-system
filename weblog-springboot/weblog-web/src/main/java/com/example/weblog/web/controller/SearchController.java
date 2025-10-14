package com.example.weblog.web.controller;

import com.example.weblog.module.common.aspect.ApiOperationLog;
import com.example.weblog.module.common.utils.Response;
import com.example.weblog.web.model.vo.search.SearcheArticlePageListReqVO;
import com.example.weblog.web.service.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "搜索")
public class SearchController {
    @Autowired
    private SearchService service;
    @PostMapping("/article/search")
    @ApiOperation(value = "文章搜索")
    @ApiOperationLog(description = "文章搜索")
    public Response searchArticlePageList(@RequestBody @Validated SearcheArticlePageListReqVO searcheArticlePageListReqVO) {
        return service.searchArticlePageList(searcheArticlePageListReqVO);
    }
}
