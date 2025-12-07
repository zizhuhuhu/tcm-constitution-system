package com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.dos.*;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.mapper.CategoryMapper;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.mapper.QuestionCategoryRelMapper;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.enums.ResponseCodeEnum;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.exception.BizException;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.model.vo.SelectRspVO;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.utils.PageResponse;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.utils.Response;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.category.FindCategoryDetailReqVO;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.category.FindCategoryDetailRspVO;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.category.*;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.service.AdminCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminCategoryServiceImpl implements AdminCategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private QuestionCategoryRelMapper questionCategoryRelMapper;
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
                .commonSymptoms(addCategoryReqVO.getCommonSymptoms())
                .characteristics(addCategoryReqVO.getCharacteristics())
                .description(addCategoryReqVO.getDescription())
                .psychologicalTraits(addCategoryReqVO.getPsychologicalTraits())
                .dietAdvice(addCategoryReqVO.getDietAdvice())
                .lifestyleAdvice(addCategoryReqVO.getLifestyleAdvice())
                .exerciseAdvice(addCategoryReqVO.getExerciseAdvice())
                .acupointAdvice(addCategoryReqVO.getAcupointAdvice())
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
                            .description(categoryDO.getDescription())
                            .characteristics(categoryDO.getCharacteristics())
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
        //校验该分类下是否已经有题目，若有，则提示需要先删除分类下所有题目才能删除
        QuestionCategoryRelDO questionCategoryRelDO = questionCategoryRelMapper.selectOneByCategoryId(categoryId);
        if(Objects.nonNull(questionCategoryRelDO)) {
            log.warn("==>此分类下包含问题，无法删除，categoryId:{}", categoryId);
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

    @Override
    public Response updateCategory(UpdateCategoryReqVO updateCategoryReqVO) {
        Long categoryId = updateCategoryReqVO.getId();
        //VO转DO,并更新
        CategoryDO categoryDO = CategoryDO.builder()
                .id(categoryId)
                .commonSymptoms(updateCategoryReqVO.getCommonSymptoms())
                .characteristics(updateCategoryReqVO.getCharacteristics())
                .description(updateCategoryReqVO.getDescription())
                .psychologicalTraits(updateCategoryReqVO.getPsychologicalTraits())
                .dietAdvice(updateCategoryReqVO.getDietAdvice())
                .lifestyleAdvice(updateCategoryReqVO.getLifestyleAdvice())
                .exerciseAdvice(updateCategoryReqVO.getExerciseAdvice())
                .acupointAdvice(updateCategoryReqVO.getAcupointAdvice())
                .updateTime(LocalDateTime.now())
                .build();
        int count = categoryMapper.updateById(categoryDO);
        //根据更新是否成功，来判断文章是否存在
        if(count == 0){
            log.warn("==>该分类不存在，categoryId: {}", categoryId);
            throw new BizException(ResponseCodeEnum.CATEGORY_NOT_EXISTED);
        }
        return Response.success();
    }

    @Override
    public Response findCategoryDetail(FindCategoryDetailReqVO findCategoryDetailReqVO) {
        Long categoryId = findCategoryDetailReqVO.getId();
        CategoryDO categoryDO = categoryMapper.selectById(categoryId);
        if(Objects.isNull(categoryDO)) {
            log.warn("==>查询的分类不存在，categoryId: {}", categoryId);
            throw new BizException(ResponseCodeEnum.ARTICLE_NOT_FOUND);
        }
        FindCategoryDetailRspVO vo = FindCategoryDetailRspVO.builder()
                .id(categoryDO.getId())
                .name(categoryDO.getName())
                .description(categoryDO.getDescription())
                .characteristics(categoryDO.getCharacteristics())
                .commonSymptoms(categoryDO.getCommonSymptoms())
                .psychologicalTraits(categoryDO.getPsychologicalTraits())
                .dietAdvice(categoryDO.getDietAdvice())
                .lifestyleAdvice(categoryDO.getLifestyleAdvice())
                .build();
        return Response.success(vo);



    }


}
