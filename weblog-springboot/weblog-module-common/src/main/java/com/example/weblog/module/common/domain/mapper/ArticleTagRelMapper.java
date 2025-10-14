package com.example.weblog.module.common.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.weblog.module.common.config.InsertBatchMapper;
import com.example.weblog.module.common.domain.dos.ArticleTagRelDO;

import java.util.List;

public interface ArticleTagRelMapper extends InsertBatchMapper<ArticleTagRelDO> {
//根据文章ID删除关联记录
    default int deleteByArticleId(Long articleId) {
        return delete(Wrappers.<ArticleTagRelDO>lambdaQuery()
            .eq(ArticleTagRelDO::getArticleId, articleId));
    }
    //根据文章id查询
    default List<ArticleTagRelDO> selectByArticleId(Long articleId){
        return selectList(Wrappers.<ArticleTagRelDO>lambdaQuery()
        .eq(ArticleTagRelDO::getArticleId, articleId));
    }
    //根据标签id查询
    default ArticleTagRelDO selectOneByTagId(Long tagId) {
        return selectOne(Wrappers.<ArticleTagRelDO>lambdaQuery()
        .eq(ArticleTagRelDO::getTagId, tagId)
        .last("LIMIT 1"));
    }
    default List<ArticleTagRelDO> selectByArticleIds(List<Long> articleIds) {
        return selectList(Wrappers.<ArticleTagRelDO>lambdaQuery()
        .in(ArticleTagRelDO::getArticleId, articleIds));
    }

}
