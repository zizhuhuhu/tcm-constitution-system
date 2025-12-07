package com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.dos.TagDO;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public interface TagMapper extends BaseMapper<TagDO> {

    //根据用户名查询
    default TagDO selectByName(String tagName){
        LambdaUpdateWrapper<TagDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(TagDO::getName, tagName);
        //执行查询
        return selectOne(wrapper);
    }
    //分页查询
    default Page<TagDO> selectPageList(long current, long size, String name, LocalDate startDate, LocalDate endDate){
        //分页对象
        Page<TagDO> page = new Page<>(current, size);
        //构建查询条件
        LambdaQueryWrapper<TagDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Objects.nonNull(name), TagDO::getName, name)
                .ge(Objects.nonNull(startDate), TagDO::getCreateTime, startDate)
                .le(Objects.nonNull(endDate), TagDO::getCreateTime, endDate)
                .orderByDesc(TagDO::getCreateTime);
        return selectPage(page, wrapper);
    }
    //模糊查询
    default List<TagDO> selectByKey(String key){
        LambdaQueryWrapper<TagDO> wrapper = new LambdaQueryWrapper();
        wrapper.like(TagDO::getName, key).orderByDesc(TagDO::getCreateTime);
        return selectList(wrapper);
    }
    //根据标签id批量查询
    default List<TagDO> selectByIds(List<Long> tagIds) {
        return selectList(Wrappers.<TagDO>lambdaQuery()
        .in(TagDO::getId, tagIds));
    }
}
