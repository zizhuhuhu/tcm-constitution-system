package com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.service.impl;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.dos.ArticleTagRelDO;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.dos.TagDO;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.mapper.ArticleTagRelMapper;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.mapper.TagMapper;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.enums.ResponseCodeEnum;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.exception.BizException;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.model.vo.SelectRspVO;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.utils.PageResponse;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.utils.Response;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.tag.*;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.service.AdminTagService;
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
public class AdminTagServiceImpl extends ServiceImpl<TagMapper, TagDO> implements AdminTagService {
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private ArticleTagRelMapper articleTagRelMapper;
    //添加分类
    @Override
    public Response addTag(AddTagReqVO addTagReqVO) {
        // vo 转 do
        List<TagDO> tagDOS = addTagReqVO.getTags()
                .stream().map(tagName -> TagDO.builder()
                .name(tagName.trim())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build())
                .collect(Collectors.toList());
        //批量插入
        try{
            saveBatch(tagDOS);
        } catch (Exception e){
            log.warn("该标签已存在", e);
            return Response.fail(ResponseCodeEnum.TAG_CANT_DUPLICATE);
        }

        return Response.success();
    }
    @Override
    public PageResponse findTagPageList(FindTagPageListReqVO findTagPageListReqVO){
        //分页参数、条件参数
        Long current = findTagPageListReqVO.getCurrent();
        Long size = findTagPageListReqVO.getSize();
        String name = findTagPageListReqVO.getName();
        LocalDate startDate = findTagPageListReqVO.getStartDate();
        LocalDate endDate = findTagPageListReqVO.getEndDate();
        //分页查询
        Page<TagDO> page = tagMapper.selectPageList(current, size, name, startDate, endDate);
        List<TagDO> records = page.getRecords();

        //Do 转 vo
        List<FindTagPageListRspVO> vos = null;
        //判断是否为空
        if(!CollectionUtils.isEmpty(records)){
            vos = records.stream().map(tagDO -> FindTagPageListRspVO.builder()
                    .id(tagDO.getId())
                    .name(tagDO.getName())
                    .createTime(tagDO.getCreateTime())
            .build()).collect(Collectors.toList());
        }

        return PageResponse.success(page, vos);
    }

    @Override
    public Response deleteTag(DeleteTagReqVO deleteTagReqVO) {
            //标签ID
            Long tagId = deleteTagReqVO.getId();
            //校验该标签下是否有关联的文章，若有，则不允许删除，提示用户需要先删除标签下的文章
        ArticleTagRelDO articleTagRelDO = articleTagRelMapper.selectOneByTagId(tagId);
        if(Objects.nonNull(articleTagRelDO)) {
            log.warn("==>此标签下包含文章，无法删除，tagId:{}", tagId);
            throw new BizException(ResponseCodeEnum.TAG_CAN_NOT_DELETE);
        }
            //删除标签
        int count = tagMapper.deleteById(tagId);
        return count == 1 ? Response.success() : Response.fail(ResponseCodeEnum.TAG_NOT_FOUND);

    }

    @Override
    public Response searchTag(SearchTagReqVO searchTagReqVO) {
        String key = searchTagReqVO.getKey();
        //根据key模糊查询
        List<TagDO> tagDOS = tagMapper.selectByKey(key);
        //do转vo
        List<SelectRspVO> vos = null;
        if(!CollectionUtils.isEmpty(tagDOS)) {
            vos = tagDOS.stream().map(tagDO -> SelectRspVO.builder()
                    .label(tagDO.getName())
                    .value(tagDO.getId())
                    .build()).collect(Collectors.toList());
        }


        return Response.success(vos);
    }

    @Override
    public Response findTagSelectList() {
        //查询所有标签，Wrappers.emptyWrapper() 表示查询条件为空
        List<TagDO> tagDOS = tagMapper.selectList(Wrappers.emptyWrapper());
        //Do转VO
        List<SelectRspVO> vos = null;
        if(!CollectionUtils.isEmpty(tagDOS)) {
            vos = tagDOS.stream().map(tagDO -> SelectRspVO.builder()
            .label(tagDO.getName())
            .value(tagDO.getId())
            .build()
            ).collect(Collectors.toList());
        }
        return Response.success(vos);

    }

}
