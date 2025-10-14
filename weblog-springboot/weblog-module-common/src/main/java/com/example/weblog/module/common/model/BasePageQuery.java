package com.example.weblog.module.common.model;

import lombok.Data;

@Data
public class BasePageQuery {
    //当前页码，默认第一页
    private Long current = 1L;
    //每页展示的数据数量，默认每页10条
    private Long size = 10L;
}
