package com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.dos.*;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.mapper.CategoryMapper;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.mapper.QuestionCategoryRelMapper;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.mapper.QuestionMapper;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.enums.QuestionEnum;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.enums.ResponseCodeEnum;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.exception.BizException;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.utils.PageResponse;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.utils.Response;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.question.FindQuestionPageListReqVO;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.question.FindQuestionPageListRspVO;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.question.*;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.service.AdminQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Service
@Slf4j
public class AdminQuestionServiceImpl implements AdminQuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private QuestionCategoryRelMapper questionCategoryRelMapper;

    @Override
    public Response findQuestionPageList(FindQuestionPageListReqVO findQuestionPageListReqVO) {
        Long current = findQuestionPageListReqVO.getCurrent();
        Long size = findQuestionPageListReqVO.getSize();
        String title = findQuestionPageListReqVO.getTitle();
        LocalDate startDate = findQuestionPageListReqVO.getStartDate();
        LocalDate endDate = findQuestionPageListReqVO.getEndDate();

        Page<QuestionDO> questionDOPage = questionMapper.selectPageList(current, size, title, startDate, endDate);
        List<QuestionDO> questionDOS = questionDOPage.getRecords();
        List<FindQuestionPageListRspVO> vos = null;

        //DO 转换成VO
        if (!CollectionUtils.isEmpty(questionDOS)) {
            vos = questionDOS.stream()
                    .map(questionDO -> {
                        // 获取枚举类型的字符串描述
                        String questionType = "";
                        if (questionDO.getQuestionType() != null) {
                            questionType = QuestionEnum.getMessageByCode(questionDO.getQuestionType());
                        }
                        Long constitutionCategoryId = questionDO.getConstitutionCategoryId();
                        CategoryDO categoryDO = categoryMapper.selectById(constitutionCategoryId);
                        return FindQuestionPageListRspVO.builder()
                                .id(questionDO.getId())
                                .title(questionDO.getTitle())
                                .questionType(questionType) // 使用转换后的字符串
                                .constitutionCategoryName(categoryDO.getName())
                                .createTime(questionDO.getCreateTime())
                                .sortOrder(questionDO.getSortOrder())
                                .build();
                    })
                    .collect(Collectors.toList());
        }
        return PageResponse.success(questionDOPage, vos);
    }

    @Override
    @Transactional
    public Response publishQuestion(PublishQuestionReqVO publishQuestionReqVO) {
        //VO转DO,并保存
        QuestionDO questionDO = QuestionDO.builder()
                .title(publishQuestionReqVO.getTitle())
                .description(publishQuestionReqVO.getDescription())
                .sortOrder(publishQuestionReqVO.getSortOrder())
                .questionType(publishQuestionReqVO.getQuestionType())
                .constitutionCategoryId(publishQuestionReqVO.getConstitutionCategoryId())
                .isRequired(publishQuestionReqVO.getIsRequired())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        questionMapper.insert(questionDO);
        Long questionId = questionDO.getId();
        //处理问题关联的分类
        Long categoryId = publishQuestionReqVO.getConstitutionCategoryId();
        //校验提交的分类是否真实存在
        CategoryDO categoryDO = categoryMapper.selectById(categoryId);
        if (Objects.isNull(categoryDO)) {
            log.warn("==>分类不存在，categoryId:{}", categoryId);
            throw new BizException(ResponseCodeEnum.CATEGORY_NOT_EXISTED);
        }
        QuestionCategoryRelDO questionCategoryRelDO = QuestionCategoryRelDO.builder()
                .questionId(questionId)
                .categoryId(categoryId)
                .build();
        questionCategoryRelMapper.insert(questionCategoryRelDO);
        return Response.success();
    }

    @Override
    public Response deleteQuestion(DeleteQuestionReqVO deleteQuestionReqVO) {
        Long questionId = deleteQuestionReqVO.getId();

        //删除文章
        questionMapper.deleteById(questionId);
        //删除问题-分类关联记录
        questionCategoryRelMapper.deleteByQuestionId(questionId);
        return Response.success();
    }

    @Override
    public Response findQuestionDetail(FindQuestionDetailReqVO findQuestionDetailReqVO) {
        Long questionId = findQuestionDetailReqVO.getId();
        QuestionDO questionDO = questionMapper.selectById(questionId);
        if(Objects.isNull(questionDO)) {
            log.warn("==>查询的问题不存在，questionId: {}", questionId);
            throw new BizException(ResponseCodeEnum.QUESTION_NOT_FOUND);
        }
        //所属分类
        QuestionCategoryRelDO questionCategoryRelDO = questionCategoryRelMapper.selectByQuestionId(questionId);
        //DO转VO
        FindQuestionDetailRspVO vo = FindQuestionDetailRspVO.builder()
                .title(questionDO.getTitle())
                .description(questionDO.getDescription())
                .sortOrder(questionDO.getSortOrder())
                .questionType(QuestionEnum.getMessageByCode(questionDO.getQuestionType()))
                .constitutionCategoryId(questionCategoryRelDO.getCategoryId())
                .isRequired(questionDO.getIsRequired())
                .build();
        return Response.success(vo);
    }

    @Override
    public Response updateQuestion(UpdateQuestionReqVO updateQuestionReqVO) {
        Long questionId = updateQuestionReqVO.getId();
        CategoryDO categoryDO = categoryMapper.selectById(updateQuestionReqVO.getConstitutionCategoryId());
        //VO转DO,并更新
        QuestionDO questionDO = QuestionDO.builder()
                .id(questionId)
                .description(updateQuestionReqVO.getDescription())
                .title(updateQuestionReqVO.getTitle())
                .sortOrder(updateQuestionReqVO.getSortOrder())
                .questionType(updateQuestionReqVO.getQuestionType())
                .constitutionCategoryId(updateQuestionReqVO.getConstitutionCategoryId())
                .build();
        int count = questionMapper.updateById(questionDO);
        //根据更新是否成功，来判断文章是否存在
        if(count == 0){
            log.warn("==>该问题不存在，questionId: {}", questionId);
            throw new BizException(ResponseCodeEnum.QUESTION_NOT_FOUND);
        }
        //更新分类
        Long categoryId = updateQuestionReqVO.getConstitutionCategoryId();
        //校验提交的分类是否真实存在
        if(Objects.isNull(categoryDO)) {
            log.warn("==> 分类不存在，categoryId: {}", categoryId);
            throw new BizException(ResponseCodeEnum.CATEGORY_NOT_EXISTED);
        }
        //先删除该文章关联的分类记录，再插入新的关联记录
        questionCategoryRelMapper.deleteByQuestionId(questionId);
        QuestionCategoryRelDO questionCategoryRelDO = QuestionCategoryRelDO.builder()
                .categoryId(categoryId)
                .questionId(questionId)
                .build();
        questionCategoryRelMapper.insert(questionCategoryRelDO);
        //保存文章关联的标签集合
        return Response.success();
    }


}
