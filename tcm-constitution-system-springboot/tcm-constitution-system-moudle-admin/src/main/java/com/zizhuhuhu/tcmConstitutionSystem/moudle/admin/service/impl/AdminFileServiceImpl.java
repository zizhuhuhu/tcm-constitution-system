package com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.service.impl;

import com.zizhuhuhu.tcmConstitutionSystem.module.common.enums.ResponseCodeEnum;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.exception.BizException;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.utils.Response;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.file.UploadFileRepVO;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.service.AdminFileService;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.utils.MinioUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class AdminFileServiceImpl implements AdminFileService {
    @Autowired
    private MinioUtil minioUtil;
    //上传文件
    @Override
    public Response uploadFile(MultipartFile file) {
        //上传文件
        String url = null;
        try {
            url = minioUtil.uploadFile(file);
            //构建反参成功，将图片的访问链接返回
            return Response.success(UploadFileRepVO.builder().url(url).build());

        } catch (Exception e) {
            log.error("==>上传文件至Minio错误：", e);
            //手动抛出业务异常，提示“文件上传失败”
            throw new BizException(ResponseCodeEnum.FILE_UPLOAD_FAILED);
        }
    }
}
