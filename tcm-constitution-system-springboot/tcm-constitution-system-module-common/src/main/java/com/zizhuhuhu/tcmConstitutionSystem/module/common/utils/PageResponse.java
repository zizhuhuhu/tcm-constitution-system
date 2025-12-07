package com.zizhuhuhu.tcmConstitutionSystem.module.common.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class PageResponse<T> extends Response<List<T>> {
    //总记录数
    private long total = 0L;
    //每页显示的记录数，默认每页显示10条
    private long size = 10L;
    //当前页码
    private long current;
    //总页数
    private long pages;
    /**
     * @param page Mybatis Plus提供的分页接口
     * @param data
     * @return
     * @param <T>
     */
    public static <T> PageResponse<T> success(IPage page, List<T> data){
        PageResponse<T> response = new PageResponse<>();
        response.setSuccess(true);
        response.setCurrent(Objects.isNull(page) ? 1L : page.getCurrent());
        response.setSize(Objects.isNull(page) ? 10L : page.getSize());
        response.setPages(Objects.isNull(page) ? 0L : page.getPages());
        response.setTotal(Objects.isNull(page) ? 0L : page.getTotal());
        response.setData(data);
        return response;
    }


    public static <T> PageResponse<T> success(long total, int current, int size, List<T> data) {
        PageResponse<T> response = new PageResponse<>();
        response.setSuccess(true);
        response.setCurrent(current);
        response.setSize(size);
        // 计算总页数
        int pages = (int) Math.ceil((double) total / size);
        response.setPages(pages);
        response.setTotal(total);
        response.setData(data);
        return response;
    }
}
