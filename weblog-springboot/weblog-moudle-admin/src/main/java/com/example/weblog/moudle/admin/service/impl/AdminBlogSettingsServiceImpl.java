package com.example.weblog.moudle.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.weblog.module.common.domain.dos.BlogSettingsDO;
import com.example.weblog.module.common.domain.mapper.BlogSettingsMapper;
import com.example.weblog.module.common.utils.Response;
import com.example.weblog.moudle.admin.convert.BlogSettingsConvert;
import com.example.weblog.moudle.admin.model.vo.blogsettings.FindBlogSettingsRspVO;
import com.example.weblog.moudle.admin.model.vo.blogsettings.UpdateBlogSettingsReqVO;
import com.example.weblog.moudle.admin.service.AdminBlogSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminBlogSettingsServiceImpl extends ServiceImpl<BlogSettingsMapper, BlogSettingsDO> implements AdminBlogSettingsService {
    @Autowired
    private BlogSettingsMapper blogSettingsMapper;
    @Override
    public Response updateBlogSettings(UpdateBlogSettingsReqVO updateBlogSettingsReqVO) {
        //VO转DO
        BlogSettingsDO blogSettingsDO = BlogSettingsConvert.INSTANCE.convertVO2DO(updateBlogSettingsReqVO);
        blogSettingsDO.setId(1L);
        //保存或更新（当前数据库中存在ID为1的记录时，则执行更新操作，否则执行插入操作）
        saveOrUpdate(blogSettingsDO);
        return Response.success();

    }

    @Override
    public Response findDetail() {
        BlogSettingsDO blogSettingsDO = blogSettingsMapper.selectById(1L);
        //DO转VO
        FindBlogSettingsRspVO vo = BlogSettingsConvert.INSTANCE.convertDO2VO(blogSettingsDO);
        return Response.success(vo);
    }
}
