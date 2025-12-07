package com.zizhuhuhu.tcmConstitutionSystem.web.service.impl;

import com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.dos.BlogSettingsDO;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.mapper.BlogSettingsMapper;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.utils.Response;
import com.zizhuhuhu.tcmConstitutionSystem.web.convert.BlogSettingsConvert;
import com.zizhuhuhu.tcmConstitutionSystem.web.model.vo.blogsettings.FindBlogSettingsDetailRspVO;
import com.zizhuhuhu.tcmConstitutionSystem.web.service.BlogSettingsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BlogSettingServiceImpl implements BlogSettingsService {
    @Autowired
    private BlogSettingsMapper blogSettingsMapper;
    @Override
    public Response findDetail() {
        //查询博客设置信息（约定ID为1）
        BlogSettingsDO blogSettingsDO = blogSettingsMapper.selectById(1L);
        //DO转VO
        FindBlogSettingsDetailRspVO vo = BlogSettingsConvert.INSTANCE.convertDO2VO(blogSettingsDO);
        vo.setZhihuHomepage(blogSettingsDO.getZhihuHomepage());
        return Response.success(vo);
    }
}
