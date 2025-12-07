package com.example.weblog.moudle.admin.service;

import com.example.weblog.module.common.utils.Response;
import com.example.weblog.moudle.admin.model.vo.artical.*;
import com.example.weblog.moudle.admin.model.vo.artical.FindQuestionPageListReqVO;

public interface AdminArticalService {

    Response publishArticle(PublishArticleReqVO publishArticleReqVO);
    Response deleteArticle(DeleteArticleReqVO deleteArticleReqVO);
    Response findArticalPageList(FindArticalPageListReqVO findArticalPageListReqVO);
    //查询文章详情
    Response findArticleDetail(FindArticleDetailReqVO findArticleDetailReqVO);
    //更新文章
    Response updateArticle(UpdateArticleReqVO updateArticleReqVO);
}
