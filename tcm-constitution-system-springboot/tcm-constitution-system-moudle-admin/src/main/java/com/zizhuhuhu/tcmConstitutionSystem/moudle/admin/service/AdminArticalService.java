package com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.service;

import com.zizhuhuhu.tcmConstitutionSystem.module.common.utils.Response;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.artical.*;

public interface AdminArticalService {

    Response publishArticle(PublishArticleReqVO publishArticleReqVO);
    Response deleteArticle(DeleteArticleReqVO deleteArticleReqVO);
    Response findArticalPageList(FindArticalPageListReqVO findArticalPageListReqVO);
    //查询文章详情
    Response findArticleDetail(FindArticleDetailReqVO findArticleDetailReqVO);
    //更新文章
    Response updateArticle(UpdateArticleReqVO updateArticleReqVO);
}
