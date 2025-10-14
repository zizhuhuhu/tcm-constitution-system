package com.example.weblog.moudle.admin.service;

import com.example.weblog.module.common.utils.Response;
import com.example.weblog.moudle.admin.model.vo.blogsettings.UpdateBlogSettingsReqVO;

public interface AdminBlogSettingsService {
    Response updateBlogSettings(UpdateBlogSettingsReqVO updateBlogSettingsReqVO);
    //获取博客设置详情
    Response findDetail();
}
