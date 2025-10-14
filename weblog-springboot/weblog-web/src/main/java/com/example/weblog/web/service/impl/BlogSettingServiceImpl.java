package com.example.weblog.web.service.impl;

import com.example.weblog.module.common.domain.dos.BlogSettingsDO;
import com.example.weblog.module.common.domain.mapper.BlogSettingsMapper;
import com.example.weblog.module.common.utils.Response;
import com.example.weblog.web.convert.BlogSettingsConvert;
import com.example.weblog.web.model.vo.blogsettings.FindBlogSettingsDetailRspVO;
import com.example.weblog.web.service.BlogSettingsService;
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
