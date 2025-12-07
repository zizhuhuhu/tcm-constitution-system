package com.example.weblog.moudle.admin.service;

import com.example.weblog.module.common.utils.PageResponse;
import com.example.weblog.module.common.utils.Response;
import com.example.weblog.moudle.admin.model.vo.artical.FindCategoryDetailReqVO;
import com.example.weblog.moudle.admin.model.vo.artical.UpdateArticleReqVO;
import com.example.weblog.moudle.admin.model.vo.category.AddCategoryReqVO;
import com.example.weblog.moudle.admin.model.vo.category.DeleteCategoryReqVO;
import com.example.weblog.moudle.admin.model.vo.category.FindCategoryPageListReqVO;
import com.example.weblog.moudle.admin.model.vo.category.UpdateCategoryReqVO;

public interface AdminCategoryService {
    Response addCategory(AddCategoryReqVO addCategoryReqVO);
    //分类分页数据查询
    PageResponse findCategoryPageList(FindCategoryPageListReqVO findCategoryPageListReqVO);
    //删除分类
    Response deleteCategory(DeleteCategoryReqVO deleteCategoryReqVO);
    //获取文章分类的Select列表数据
    Response findCategorySelectList();
    Response updateCategory(UpdateCategoryReqVO updateCategoryReqVO);
    Response findCategoryDetail(FindCategoryDetailReqVO findCategoryDetailReqVO);
}
