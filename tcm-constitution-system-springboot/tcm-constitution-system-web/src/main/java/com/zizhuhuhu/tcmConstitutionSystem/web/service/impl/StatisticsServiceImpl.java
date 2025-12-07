package com.zizhuhuhu.tcmConstitutionSystem.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.dos.ArticleDO;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.mapper.ArticalMapper;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.mapper.CategoryMapper;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.mapper.TagMapper;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.utils.Response;
import com.zizhuhuhu.tcmConstitutionSystem.web.model.vo.statistics.FindStatisticsInfoRspVO;
import com.zizhuhuhu.tcmConstitutionSystem.web.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class StatisticsServiceImpl implements StatisticsService {
@Autowired
private ArticalMapper articalMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private TagMapper tagMapper;

    @Override
    public Response findInfo() {
        // 查询文章总数
        Long articleTotalCount = articalMapper.selectCount(Wrappers.emptyWrapper());

        // 查询分类总数
        Long categoryTotalCount = categoryMapper.selectCount(Wrappers.emptyWrapper());

        // 查询标签总数
        Long tagTotalCount = tagMapper.selectCount(Wrappers.emptyWrapper());

        // 总浏览量
        List<ArticleDO> articleDOS = articalMapper.selectAllReadNum();
        Long pvTotalCount = 0L;
        if(!CollectionUtils.isEmpty(articleDOS)) {
            pvTotalCount = articleDOS.stream().mapToLong(ArticleDO::getReadNum).sum();
        }
        FindStatisticsInfoRspVO vo = FindStatisticsInfoRspVO.builder()
                .articleTotalCount(articleTotalCount)
                .pvTotalCount(pvTotalCount)
                .tagTotalCount(tagTotalCount)
                .categoryTotalCount(categoryTotalCount)
                .build();
        return Response.success(vo);
    }
}
