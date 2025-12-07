package com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.controller;

import com.zizhuhuhu.tcmConstitutionSystem.module.common.aspect.ApiOperationLog;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.utils.PageResponse;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.utils.Response;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.tag.AddTagReqVO;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.tag.DeleteTagReqVO;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.tag.FindTagPageListReqVO;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.tag.SearchTagReqVO;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.service.AdminTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/tag")
@Api(tags = "Admin标签模块")
public class AdminTagController {
    @Autowired
    private AdminTagService tagService;
    @PostMapping("/add")
    @ApiOperationLog(description = "添加标签")
    @ApiOperation(value = "添加标签")
    public Response addCategory(@RequestBody @Validated AddTagReqVO addTagReqVO){
        return tagService.addTag(addTagReqVO);
    }
    @PostMapping("/list")
    @ApiOperationLog(description = "标签数据获取")
    @ApiOperation(value = "标签数据获取")
    public PageResponse findCategoryList(@RequestBody @Validated FindTagPageListReqVO findTagPageListReqVO){
        return tagService.findTagPageList(findTagPageListReqVO);
    }
    @PostMapping("/delete")
    @ApiOperation(value = "删除标签")
    @ApiOperationLog(description = "删除标签")
    public Response deleteTag(@RequestBody @Validated DeleteTagReqVO deleteTagReqVO){
        return tagService.deleteTag(deleteTagReqVO);
    }
    @PostMapping("/search")
    @ApiOperation(value = "标签模糊查询")
    @ApiOperationLog(description = "标签模糊查询")
    public Response searchTag(@RequestBody @Validated SearchTagReqVO searchTagReqVO){
        return tagService.searchTag(searchTagReqVO);
    }
    @PostMapping("/select/list")
    @ApiOperation(value = "标签查询Select列表数据")
    @ApiOperationLog(description = "标签查询Select列表数据")
    public Response findTagSelectList(){
        return tagService.findTagSelectList();
    }


}
