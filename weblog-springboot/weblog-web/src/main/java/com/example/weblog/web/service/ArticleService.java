package com.example.weblog.web.service;

import com.example.weblog.module.common.utils.Response;
import com.example.weblog.web.model.vo.article.FindArticleDetailReqVO;
import com.example.weblog.web.model.vo.article.FindIndexArticlePageListReqVO;

public interface ArticleService {
    //获取首页文章分页数据
    Response findArticlePageList(FindIndexArticlePageListReqVO findIndexArticlePageListReqVO);
    //获取文章详情
    Response findArticleDetail(FindArticleDetailReqVO findArticleDetailReqVO);

}
