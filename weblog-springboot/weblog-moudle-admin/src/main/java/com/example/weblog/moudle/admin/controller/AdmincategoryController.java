package com.example.weblog.moudle.admin.controller;

import com.example.weblog.module.common.aspect.ApiOperationLog;
import com.example.weblog.module.common.utils.PageResponse;
import com.example.weblog.module.common.utils.Response;
import com.example.weblog.moudle.admin.model.vo.category.AddCategoryReqVO;
import com.example.weblog.moudle.admin.model.vo.category.DeleteCategoryReqVO;
import com.example.weblog.moudle.admin.model.vo.category.FindCategoryPageListReqVO;
import com.example.weblog.moudle.admin.service.AdminCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Api(tags = "Admin分类模块")
public class AdmincategoryController {
    @Autowired
    private AdminCategoryService categoryService;
    @PostMapping("/category/add")
    @ApiOperationLog(description = "添加分类")
    @ApiOperation(value = "添加分类")
    public Response addCategory(@RequestBody @Validated AddCategoryReqVO addCategoryReqVO){
        return categoryService.addCategory(addCategoryReqVO);
    }
    @PostMapping("/category/list")
    @ApiOperationLog(description = "分类分页数据获取")
    @ApiOperation(value = "分类分页数据获取")
    public PageResponse findCategoryPageList(@RequestBody @Validated FindCategoryPageListReqVO findCategoryPageListReqVO){
        return categoryService.findCategoryPageList(findCategoryPageListReqVO);
    }
    @PostMapping("/category/delete")
    @ApiOperation(value = "删除分类")
    @ApiOperationLog(description = "删除分类")
    public Response deleteCategory(@RequestBody @Validated DeleteCategoryReqVO deleteCategoryReqVO){
        return categoryService.deleteCategory(deleteCategoryReqVO);
    }
    @PostMapping("/category/select/list")
    @ApiOperationLog(description = "分类Select 下拉列表数据获取")
    @ApiOperation(value = "分类Select 下拉列表数据获取")
    public Response findCategorySelectList(){
        return categoryService.findCategorySelectList();
    }

}
