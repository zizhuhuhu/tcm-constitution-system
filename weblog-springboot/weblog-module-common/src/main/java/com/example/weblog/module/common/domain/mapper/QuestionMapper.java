package com.example.weblog.module.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.weblog.module.common.domain.dos.ArticleDO;
import com.example.weblog.module.common.domain.dos.QuestionDO;

import java.time.LocalDate;
import java.util.Objects;

public interface QuestionMapper extends BaseMapper<QuestionDO> {
    /**
     * 分页查询
     * @param current 当前页码
     * @param size 每页展示的数据量
     * @param title 文章标题
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    default Page<QuestionDO> selectPageList(Long current, Long size, String title, LocalDate startDate, LocalDate endDate){
        //分页对象（查询第几页，每页多少数据）
        Page<QuestionDO> page = new Page<>(current, size);
        //构建查询条件
        LambdaQueryWrapper<QuestionDO> wrapper = Wrappers.<QuestionDO>lambdaQuery()
                .like(StringUtils.isNotBlank(title), QuestionDO::getTitle, title)
                .ge(Objects.nonNull(startDate), QuestionDO::getCreateTime, startDate)
                .le(Objects.nonNull(endDate), QuestionDO::getCreateTime, endDate)
                .orderByDesc(QuestionDO::getCreateTime);
        return selectPage(page, wrapper);

    }
    //查询上一篇文章
    default QuestionDO selectPrequestion(Long articleId) {
        return selectOne(Wrappers.<QuestionDO>lambdaQuery()
                .orderByAsc(QuestionDO::getId) //按文章ID升序排列
                .gt(QuestionDO::getId, articleId) //查询比当前问题ID大的
                .last("limit 1"));
    }
    //查询下一篇文章
    default QuestionDO selectNextQuestion(Long questionId) {
        return selectOne(Wrappers.<QuestionDO>lambdaQuery()
                .orderByDesc(QuestionDO::getId) //按文章ID升序排列
                .lt(QuestionDO::getId, questionId) //查询比当前问题ID大的
                .last("limit 1"));
    }
}
