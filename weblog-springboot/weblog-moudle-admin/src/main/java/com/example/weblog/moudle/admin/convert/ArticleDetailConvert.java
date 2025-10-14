package com.example.weblog.moudle.admin.convert;

import com.example.weblog.module.common.domain.dos.ArticleDO;
import com.example.weblog.moudle.admin.model.vo.artical.FindArticleDetailRspVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArticleDetailConvert {
    //初始化convert实例
    ArticleDetailConvert INSTANCE = Mappers.getMapper(ArticleDetailConvert.class);
    //将DO转成VO
    FindArticleDetailRspVO convertDO2VO(ArticleDO bean);

}
