package com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.service;

import com.zizhuhuhu.tcmConstitutionSystem.module.common.utils.PageResponse;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.utils.Response;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.category.FindCategoryDetailReqVO;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.category.AddCategoryReqVO;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.category.DeleteCategoryReqVO;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.category.FindCategoryPageListReqVO;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.category.UpdateCategoryReqVO;

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
