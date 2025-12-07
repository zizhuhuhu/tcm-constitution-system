package com.example.weblog.moudle.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.weblog.module.common.constant.Constants;
import com.example.weblog.module.common.domain.dos.ArticleDO;
import com.example.weblog.module.common.domain.dos.ArticlePublishCountDO;
import com.example.weblog.module.common.domain.dos.StatisticsArticlePVDO;
import com.example.weblog.module.common.domain.mapper.ArticalMapper;
import com.example.weblog.module.common.domain.mapper.CategoryMapper;
import com.example.weblog.module.common.domain.mapper.StatisticsArticlePVMapper;
import com.example.weblog.module.common.domain.mapper.TagMapper;
import com.example.weblog.module.common.utils.Response;
import com.example.weblog.moudle.admin.model.vo.dashboard.FindDashboardPVStatisticsInfoRspVO;
import com.example.weblog.moudle.admin.model.vo.dashboard.FindDashboardStatisticsInfoRspVO;
import com.example.weblog.moudle.admin.service.AdminDashboardService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminDashboardServiceImpl implements AdminDashboardService {
    @Autowired
    private ArticalMapper articalMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private StatisticsArticlePVMapper articlePVMapper;

    @Override
    public Response findDashboardStatistics() {
        //查询文章总数
        Long articleTotalCount = articalMapper.selectCount(Wrappers.emptyWrapper());
        //查询分类总数
        Long categoryTotalCount = categoryMapper.selectCount(Wrappers.emptyWrapper());
        //查询标签总数
        Long tagTotalCount = tagMapper.selectCount(Wrappers.emptyWrapper());
        //总浏览量
        List<ArticleDO> articleDOS = articalMapper.selectAllReadNum();
        Long pvTotalCount = 0L;
        if(!CollectionUtils.isEmpty(articleDOS)) {
            //所有read_num相加
            pvTotalCount = articleDOS.stream().mapToLong(ArticleDO::getReadNum).sum();
        }
        //组装vo类
        FindDashboardStatisticsInfoRspVO vo = FindDashboardStatisticsInfoRspVO.builder()
                .articleTotalCount(articleTotalCount)
                .categoryTotalCount(categoryTotalCount)
                .tagTotalCount(tagTotalCount)
                .pyTotalCount(pvTotalCount)
                .build();
        return Response.success(vo);
    }

    @Override
    public Response findDashboardPublishArticleStatistics() {
        //当前日期
        LocalDate currDate = LocalDate.now();
        //当前日期倒退一年的日期
        LocalDate startDate = currDate.minusYears(1);
        //查找这一年内，每日发布的文章数量
        List<ArticlePublishCountDO> articlePublishCountDOS = articalMapper.selectDateArticlePublishCount(startDate, currDate);
        Map<LocalDate, Long> map = null;
        if(!CollectionUtils.isEmpty(articlePublishCountDOS)) {
            //DO转Map
            Map<LocalDate, Long> dateArticleCountMap = articlePublishCountDOS.stream()
                    .collect(Collectors.toMap(ArticlePublishCountDO::getDate, ArticlePublishCountDO::getCount));
            //有序Map,返回的日期文章数以升序排列
            map = Maps.newLinkedHashMap();
            //从上一年的今天循环到今天
            for(; startDate.isBefore(currDate) || startDate.isEqual(currDate); startDate = startDate.plusDays(1)) {
                //以日期作为key从dateArticleCountMap中取文章发布总量
                Long count = dateArticleCountMap.get(startDate);
                //设置到反参Map
                map.put(startDate, Objects.isNull(count) ? 0 : count);
            }

        }
        return Response.success(map);


    }

    @Override
    public Response findDashboardPVStatics() {
        //查寻最近一周的PV访问量记录
        List<StatisticsArticlePVDO> articlePVDOS = articlePVMapper.selectLatesWeekRecords();
        Map<LocalDate, Long> pvDateCountMap = Maps.newHashMap();
        if(!CollectionUtils.isEmpty(articlePVDOS)) {
            //转Map, 方便后续通过日期获取PV访问量
            pvDateCountMap = articlePVDOS.stream()
                    .collect(Collectors.toMap(StatisticsArticlePVDO::getPvDate, StatisticsArticlePVDO::getPvCount));
        }
        FindDashboardPVStatisticsInfoRspVO vo = null;
        //日期集合
        List<String> pvDates = Lists.newArrayList();
        //pv集合
        List<Long> pvCounts = Lists.newArrayList();
        //当前日期
        LocalDate currDate = LocalDate.now();
        //一周前
        LocalDate tmpDate = currDate.minusWeeks(1);
        for(; tmpDate.isBefore(currDate) || tmpDate.isEqual(currDate); tmpDate = tmpDate.plusDays(1)) {
            //设置对应日期的PV访问量
            pvDates.add(tmpDate.format(Constants.MONTH_DAY_FORMATTER));
            Long pvCount = pvDateCountMap.get(tmpDate);
            pvCounts.add(Objects.isNull(pvCount) ? 0 : pvCount);
        }
        vo = FindDashboardPVStatisticsInfoRspVO.builder()
                .pvCounts(pvCounts)
                .pvDate(pvDates)
                .build();
        return Response.success(vo);
    }
}
