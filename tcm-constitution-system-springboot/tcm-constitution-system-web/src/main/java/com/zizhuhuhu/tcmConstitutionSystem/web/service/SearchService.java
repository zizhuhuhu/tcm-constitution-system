package com.zizhuhuhu.tcmConstitutionSystem.web.service;

import com.zizhuhuhu.tcmConstitutionSystem.module.common.utils.Response;
import com.zizhuhuhu.tcmConstitutionSystem.web.model.vo.search.SearcheArticlePageListReqVO;

public interface SearchService {
    //关键词分页搜索
    Response searchArticlePageList(SearcheArticlePageListReqVO searcheArticlePageListReqVO);
}
