package com.zizhuhuhu.tcmConstitutionSystem.web.service;

import com.zizhuhuhu.tcmConstitutionSystem.module.common.utils.Response;
import com.zizhuhuhu.tcmConstitutionSystem.web.model.vo.article.FindArticleDetailReqVO;
import com.zizhuhuhu.tcmConstitutionSystem.web.model.vo.article.FindIndexArticlePageListReqVO;

public interface ArticleService {
    //获取首页文章分页数据
    Response findArticlePageList(FindIndexArticlePageListReqVO findIndexArticlePageListReqVO);
    //获取文章详情
    Response findArticleDetail(FindArticleDetailReqVO findArticleDetailReqVO);

}
