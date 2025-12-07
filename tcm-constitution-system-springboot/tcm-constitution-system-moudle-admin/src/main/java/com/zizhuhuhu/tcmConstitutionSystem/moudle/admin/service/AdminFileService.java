package com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.service;

import com.zizhuhuhu.tcmConstitutionSystem.module.common.utils.Response;
import org.springframework.web.multipart.MultipartFile;

public interface AdminFileService {
    Response uploadFile(MultipartFile file);

}
