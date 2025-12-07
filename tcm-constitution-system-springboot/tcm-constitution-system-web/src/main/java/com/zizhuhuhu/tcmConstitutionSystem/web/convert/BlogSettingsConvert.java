package com.zizhuhuhu.tcmConstitutionSystem.web.convert;

import com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.dos.BlogSettingsDO;
import com.zizhuhuhu.tcmConstitutionSystem.web.model.vo.blogsettings.FindBlogSettingsDetailRspVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BlogSettingsConvert {
    BlogSettingsConvert INSTANCE = Mappers.getMapper(BlogSettingsConvert.class);
    //将DO转化为VO
    FindBlogSettingsDetailRspVO convertDO2VO(BlogSettingsDO bean);
}
