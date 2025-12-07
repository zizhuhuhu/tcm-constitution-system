package com.zizhuhuhu.tcmConstitutionSystem.web.model.vo.article;

import com.zizhuhuhu.tcmConstitutionSystem.module.common.model.BasePageQuery;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(value = "首页查询文章分页Vo")
public class FindIndexArticlePageListReqVO extends BasePageQuery {
}
