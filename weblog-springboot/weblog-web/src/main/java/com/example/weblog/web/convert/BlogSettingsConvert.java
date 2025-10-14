package com.example.weblog.web.convert;

import com.example.weblog.module.common.domain.dos.BlogSettingsDO;
import com.example.weblog.web.model.vo.blogsettings.FindBlogSettingsDetailRspVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BlogSettingsConvert {
    BlogSettingsConvert INSTANCE = Mappers.getMapper(BlogSettingsConvert.class);
    //将DO转化为VO
    FindBlogSettingsDetailRspVO convertDO2VO(BlogSettingsDO bean);
}
