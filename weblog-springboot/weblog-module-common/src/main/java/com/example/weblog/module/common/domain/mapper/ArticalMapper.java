package com.example.weblog.module.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.weblog.module.common.domain.dos.ArticleDO;
import com.example.weblog.module.common.domain.dos.ArticlePublishCountDO;
import org.apache.ibatis.annotations.Select;


import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public interface ArticalMapper extends BaseMapper<ArticleDO> {

    /**
     * 分页查询
     * @param current 当前页码
     * @param size 每页展示的数据量
     * @param title 文章标题
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    default Page<ArticleDO> selectPageList(Long current, Long size, String title, LocalDate startDate, LocalDate endDate){
        //分页对象（查询第几页，每页多少数据）
        Page<ArticleDO> page = new Page<>(current, size);
        //构建查询条件
        LambdaQueryWrapper<ArticleDO> wrapper = Wrappers.<ArticleDO>lambdaQuery()
                .like(StringUtils.isNotBlank(title), ArticleDO::getTitle, title)
                .ge(Objects.nonNull(startDate), ArticleDO::getCreateTime, startDate)
                .le(Objects.nonNull(endDate), ArticleDO::getCreateTime, endDate)
                .orderByDesc(ArticleDO::getCreateTime);
        return selectPage(page, wrapper);

    }
    //查询上一篇文章
    default ArticleDO selectPreArticle(Long articleId) {
        return selectOne(Wrappers.<ArticleDO>lambdaQuery()
        .orderByAsc(ArticleDO::getId) //按文章ID升序排列
        .gt(ArticleDO::getId, articleId) //查询比当前文章ID大的
        .last("limit 1"));
    }
    //查询下一篇文章
    default ArticleDO selectNextArticle(Long articleId) {
        return selectOne(Wrappers.<ArticleDO>lambdaQuery()
                .orderByDesc(ArticleDO::getId) //按文章ID升序排列
                .lt(ArticleDO::getId, articleId) //查询比当前文章ID大的
                .last("limit 1"));
    }
    //阅读量+1
    default int increaseReadNum(Long articleId) {
        //update t_article set read_num = read_num + 1 where id = xx
        return update(null, Wrappers.<ArticleDO>lambdaUpdate()
           .setSql("read_num = read_num + 1")
            .eq(ArticleDO::getId, articleId));
    }
    default List<ArticleDO> selectAllReadNum() {
        //设置仅查询read_num字段
        return selectList(Wrappers.<ArticleDO>lambdaQuery()
        .select(ArticleDO::getReadNum));
    }
    @Select("select DATE(create_time) AS date, COUNT(*) AS count\n" +
            "FROM t_article\n" +
            "WHERE create_time >= #{startDate} AND create_time < #{endDate}\n" +
            "GROUP BY DATE(create_time)")
    List<ArticlePublishCountDO> selectDateArticlePublishCount(LocalDate startDate, LocalDate endDate);
}
