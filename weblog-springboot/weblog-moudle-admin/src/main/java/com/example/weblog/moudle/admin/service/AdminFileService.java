package com.example.weblog.moudle.admin.service;

import com.example.weblog.module.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootVersion;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface AdminFileService {
    Response uploadFile(MultipartFile file);

}
