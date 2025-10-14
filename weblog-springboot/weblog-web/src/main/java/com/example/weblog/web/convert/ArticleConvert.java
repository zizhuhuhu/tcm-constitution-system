package com.example.weblog.web.convert;

import com.example.weblog.module.common.domain.dos.ArticleDO;
import com.example.weblog.moudle.admin.convert.ArticleDetailConvert;
import com.example.weblog.web.model.vo.article.FindIndexArticlePageListReqVO;
import com.example.weblog.web.model.vo.article.FindIndexArticlePageListRspVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper
public interface ArticleConvert {
    //初始化convert实例
    ArticleConvert INSTANCE = Mappers.getMapper(ArticleConvert.class);
    //将Do转成VO
    @Mapping(target = "createDate", expression = "java(java.time.LocalDate.from(bean.getCreateTime()))")
    FindIndexArticlePageListRspVO convertDO2VO(ArticleDO bean);

}
