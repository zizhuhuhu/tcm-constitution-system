package com.example.weblog.moudle.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.weblog.module.common.domain.dos.ArticleCategoryRelDO;
import com.example.weblog.module.common.domain.dos.CategoryDO;
import com.example.weblog.module.common.domain.mapper.ArticleCategoryRelMapper;
import com.example.weblog.module.common.domain.mapper.CategoryMapper;
import com.example.weblog.module.common.enums.ResponseCodeEnum;
import com.example.weblog.module.common.exception.BizException;
import com.example.weblog.module.common.model.vo.SelectRspVO;
import com.example.weblog.module.common.utils.PageResponse;
import com.example.weblog.module.common.utils.Response;
import com.example.weblog.moudle.admin.model.vo.category.AddCategoryReqVO;
import com.example.weblog.moudle.admin.model.vo.category.DeleteCategoryReqVO;
import com.example.weblog.moudle.admin.model.vo.category.FindCategoryPageListReqVO;
import com.example.weblog.moudle.admin.model.vo.category.FindCategoryPageListRspVO;
import com.example.weblog.moudle.admin.service.AdminCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminCategoryServiceImpl implements AdminCategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ArticleCategoryRelMapper articleCategoryRelMapper;
    //添加分类
    @Override
    public Response addCategory(AddCategoryReqVO addCategoryReqVO) {
        String categoryName = addCategoryReqVO.getName();
        //先判断该分类是否存在
        CategoryDO categoryDO = categoryMapper.selectByName(categoryName);
        if(Objects.nonNull(categoryDO)){
            log.warn("分类名称：{}，此分类已存在",categoryName);
            throw new BizException(ResponseCodeEnum.CATEGORY_NAME_IS_EXISTED);

        }
        //构建DO类
        CategoryDO insertCategoryDO = categoryDO.builder()
                .name(addCategoryReqVO.getName().trim())
                .build();
        //执行Insert
        categoryMapper.insert(insertCategoryDO);
        return Response.success();
    }

    @Override
    public PageResponse findCategoryPageList(FindCategoryPageListReqVO findCategoryPageListReqVO) {
        //获取当前页、以及每页需要展示的数据数量
        Long current = findCategoryPageListReqVO.getCurrent();
        Long size = findCategoryPageListReqVO.getSize();
        String name = findCategoryPageListReqVO.getName();
        LocalDate startDate = findCategoryPageListReqVO.getStartDate();
        LocalDate endDate = findCategoryPageListReqVO.getEndDate();
        //执行分页查询
        Page<CategoryDO> categoryDOPage = categoryMapper.selectPageList(current, size, name, startDate, endDate);
        List<CategoryDO> categoryDOS = categoryDOPage.getRecords();
        //DO转VO
        List<FindCategoryPageListRspVO> vos = null;
        if(!CollectionUtils.isEmpty(categoryDOS)){
            vos = categoryDOS.stream()
                    .map(categoryDO -> FindCategoryPageListRspVO.builder()
                    .id(categoryDO.getId())
                    .name(categoryDO.getName())
                    .createTime(categoryDO.getCreateTime())
                    .build())
                    .collect(Collectors.toList());

        }
        return PageResponse.success(categoryDOPage, vos);
    }

    @Override
    public Response deleteCategory(DeleteCategoryReqVO deleteCategoryReqVO) {
        //分类ID
        Long categoryId = deleteCategoryReqVO.getId();
        //校验该分类下是否已经有文章，若有，则提示需要先删除分类下所有文章才能删除
        ArticleCategoryRelDO articleCategoryRelDO = articleCategoryRelMapper.selectOneByCategoryId(categoryId);
        if(Objects.nonNull(articleCategoryRelDO)) {
            log.warn("==>此分类下包含文章，无法删除，categoryId:{}", categoryId);
            throw new BizException(ResponseCodeEnum.CATEGORY_CAN_NOT_DELETE);
        }
        //删除分类
        categoryMapper.deleteById(categoryId);
        return Response.success();
    }

    @Override
    public Response findCategorySelectList() {
        //查询所有分类
        List<CategoryDO> categoryDOS = categoryMapper.selectList(null);
        //DO转VO
        List<SelectRspVO> selectRspVOS = null;
        //如果分类数据不为空
        if(!CollectionUtils.isEmpty(categoryDOS)){
            //将分类ID作为Value值，将分类名称作为label展示
            selectRspVOS = categoryDOS.stream()
                    .map(categoryDO -> SelectRspVO.builder()
                            .label(categoryDO.getName())
                            .value(categoryDO.getId())
                            .build())
                    .collect(Collectors.toList());
        }

        return Response.success(selectRspVOS);
    }



}
