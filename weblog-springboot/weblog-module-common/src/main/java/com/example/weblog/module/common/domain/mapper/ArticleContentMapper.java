package com.example.weblog.module.common.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.weblog.module.common.domain.dos.ArticleContentDO;

import java.sql.Wrapper;

public interface ArticleContentMapper extends BaseMapper<ArticleContentDO> {
    //根据文章ID删除记录
    default int deleteByArticleId(Long articleId){
        return delete(Wrappers.<ArticleContentDO>lambdaQuery()
        .eq(ArticleContentDO::getArticleId, articleId));
    }
    //根据文章id查询
    default ArticleContentDO selectByArticleId(Long articleId){
        return selectOne(Wrappers.<ArticleContentDO>lambdaQuery()
                .eq(ArticleContentDO::getArticleId, articleId));
    }
    //通过文章id更新
    default int updateByArticleId(ArticleContentDO articleContentDO) {
        return update(articleContentDO, Wrappers.<ArticleContentDO>lambdaQuery()
        .eq(ArticleContentDO::getArticleId, articleContentDO.getArticleId()));
    }
}
