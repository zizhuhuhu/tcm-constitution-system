package com.example.weblog.moudle.admin.service;
import com.example.weblog.module.common.utils.PageResponse;
import com.example.weblog.module.common.utils.Response;
import com.example.weblog.moudle.admin.model.vo.category.AddCategoryReqVO;
import com.example.weblog.moudle.admin.model.vo.category.DeleteCategoryReqVO;
import com.example.weblog.moudle.admin.model.vo.category.FindCategoryPageListReqVO;
import com.example.weblog.moudle.admin.model.vo.tag.AddTagReqVO;
import com.example.weblog.moudle.admin.model.vo.tag.DeleteTagReqVO;
import com.example.weblog.moudle.admin.model.vo.tag.FindTagPageListReqVO;
import com.example.weblog.moudle.admin.model.vo.tag.SearchTagReqVO;

public interface AdminTagService {
    Response addTag(AddTagReqVO addTagReqVO);
    //标签数据查询
    PageResponse findTagPageList(FindTagPageListReqVO findTagPageListReqVO);
    //删除标签
    Response deleteTag(DeleteTagReqVO deleteTagReqVO);
    //模糊查询标签
    Response searchTag(SearchTagReqVO searchTagReqVO);
    //查询标签select列表数据
    Response findTagSelectList();
}

