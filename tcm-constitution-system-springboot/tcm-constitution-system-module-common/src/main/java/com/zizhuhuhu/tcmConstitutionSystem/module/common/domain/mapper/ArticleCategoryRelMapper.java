package com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.dos.ArticleCategoryRelDO;

import java.util.List;

public interface ArticleCategoryRelMapper extends BaseMapper<ArticleCategoryRelDO> {
//根据文章ID删除关联记录
    default int deleteByArticleId(Long articleId){
        return delete(Wrappers.<ArticleCategoryRelDO>lambdaQuery()
            .eq(ArticleCategoryRelDO::getArticleId, articleId));
    }
    //根据文章id查询
    default ArticleCategoryRelDO selectByArticleId(Long articleId){
        return selectOne(Wrappers.<ArticleCategoryRelDO>lambdaQuery()
        .eq(ArticleCategoryRelDO::getArticleId, articleId));

    }
    //根据分类id查询
    default ArticleCategoryRelDO selectOneByCategoryId(Long categoryId) {
        return selectOne(Wrappers.<ArticleCategoryRelDO>lambdaQuery()
        .eq(ArticleCategoryRelDO::getCategoryId, categoryId)
        .last("LIMIT 1"));
    }
    default List<ArticleCategoryRelDO> selectByArticleIds(List<Long> articleIds){
        return selectList(Wrappers.<ArticleCategoryRelDO>lambdaQuery()
                .in(ArticleCategoryRelDO::getArticleId, articleIds));
    }



}
