package com.example.weblog.web.service;

import com.example.weblog.module.common.utils.Response;

import java.lang.module.ResolutionException;

public interface BlogSettingsService {
    //获取博客设置信息
    Response findDetail();
}
