package com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.convert;

import com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.dos.ArticleDO;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.artical.FindArticleDetailRspVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArticleDetailConvert {
    //初始化convert实例
    ArticleDetailConvert INSTANCE = Mappers.getMapper(ArticleDetailConvert.class);
    //将DO转成VO
    FindArticleDetailRspVO convertDO2VO(ArticleDO bean);

}
