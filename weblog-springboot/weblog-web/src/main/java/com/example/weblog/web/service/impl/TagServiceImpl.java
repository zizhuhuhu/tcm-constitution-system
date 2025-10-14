package com.example.weblog.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.weblog.module.common.domain.dos.TagDO;
import com.example.weblog.module.common.domain.mapper.TagMapper;
import com.example.weblog.module.common.utils.Response;
import com.example.weblog.web.model.vo.tag.FindTagListRspVO;
import com.example.weblog.web.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;
    @Override
    public Response findTagList() {
        List<TagDO> tagDOS = tagMapper.selectList(Wrappers.emptyWrapper());

        //Do转Vo
        List<FindTagListRspVO> vos = null;
        if(!CollectionUtils.isEmpty(tagDOS)) {
            vos = tagDOS.stream().map(tagDO -> FindTagListRspVO.builder()
            .id(tagDO.getId())
            .name(tagDO.getName())
            .build())
                    .collect(Collectors.toList());
        }
        return Response.success(vos);
    }
}
