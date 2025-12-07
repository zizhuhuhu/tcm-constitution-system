package com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.service;

import com.zizhuhuhu.tcmConstitutionSystem.module.common.utils.Response;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.blogsettings.UpdateBlogSettingsReqVO;

public interface AdminBlogSettingsService {
    Response updateBlogSettings(UpdateBlogSettingsReqVO updateBlogSettingsReqVO);
    //获取博客设置详情
    Response findDetail();
}
