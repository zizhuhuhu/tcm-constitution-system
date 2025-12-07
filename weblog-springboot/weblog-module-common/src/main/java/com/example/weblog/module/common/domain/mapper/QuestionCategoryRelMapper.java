package com.example.weblog.module.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.weblog.module.common.domain.dos.ArticleCategoryRelDO;
import com.example.weblog.module.common.domain.dos.QuestionCategoryRelDO;
public interface QuestionCategoryRelMapper extends BaseMapper<QuestionCategoryRelDO> {
    default int deleteByQuestionId(Long questionId) {
/*
          return delete(null, new QueryWrapper<QuestionCategoryRelDO>().eq("question_id", questionId));
*/
        return delete(Wrappers.<QuestionCategoryRelDO>lambdaQuery()
                .eq(QuestionCategoryRelDO::getQuestionId, questionId));
    }
    //根据文章id查询
    default QuestionCategoryRelDO selectByQuestionId(Long questionId){
        return selectOne(Wrappers.<QuestionCategoryRelDO>lambdaQuery()
                .eq(QuestionCategoryRelDO::getQuestionId, questionId));

    }
    //根据分类id查询
    default QuestionCategoryRelDO selectOneByCategoryId(Long categoryId) {
        return selectOne(Wrappers.<QuestionCategoryRelDO>lambdaQuery()
                .eq(QuestionCategoryRelDO::getCategoryId, categoryId)
                .last("LIMIT 1"));
    }

}
