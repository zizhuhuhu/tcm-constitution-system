package com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.dos.CategoryDO;

import java.time.LocalDate;
import java.util.Objects;

public interface CategoryMapper extends BaseMapper<CategoryDO> {

    //根据用户名查询
    default CategoryDO selectByName(String categoryName) {
        LambdaUpdateWrapper<CategoryDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(CategoryDO::getName, categoryName);
        //执行查询
        return selectOne(wrapper);
    }

    default Page<CategoryDO> selectPageList(long current, long size, String name, LocalDate startDate, LocalDate endDate) {
        //分页对象
        Page<CategoryDO> page = new Page<>(current, size);
        //构建查询条件
        LambdaQueryWrapper<CategoryDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Objects.nonNull(name), CategoryDO::getName, name)
                .ge(Objects.nonNull(startDate), CategoryDO::getCreateTime, startDate)
                .le(Objects.nonNull(endDate), CategoryDO::getCreateTime, endDate)
                .orderByDesc(CategoryDO::getCreateTime);
        return selectPage(page, wrapper);
    }
}
