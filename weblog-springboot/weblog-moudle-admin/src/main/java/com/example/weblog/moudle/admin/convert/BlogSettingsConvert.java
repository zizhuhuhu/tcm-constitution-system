package com.example.weblog.moudle.admin.convert;

import com.example.weblog.module.common.domain.dos.BlogSettingsDO;
import com.example.weblog.moudle.admin.model.vo.blogsettings.FindBlogSettingsRspVO;
import com.example.weblog.moudle.admin.model.vo.blogsettings.UpdateBlogSettingsReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface BlogSettingsConvert {
    //初始化convert实例
    BlogSettingsConvert INSTANCE = Mappers.getMapper(BlogSettingsConvert.class);
    //VO转DO
    BlogSettingsDO convertVO2DO(UpdateBlogSettingsReqVO bean);
    //DO转VO
    FindBlogSettingsRspVO convertDO2VO(BlogSettingsDO bean);
}
