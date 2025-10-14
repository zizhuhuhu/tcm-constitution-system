package com.example.weblog.web.service;

import com.example.weblog.module.common.utils.Response;
import com.example.weblog.web.model.vo.search.SearcheArticlePageListReqVO;

public interface SearchService {
    //关键词分页搜索
    Response searchArticlePageList(SearcheArticlePageListReqVO searcheArticlePageListReqVO);
}
