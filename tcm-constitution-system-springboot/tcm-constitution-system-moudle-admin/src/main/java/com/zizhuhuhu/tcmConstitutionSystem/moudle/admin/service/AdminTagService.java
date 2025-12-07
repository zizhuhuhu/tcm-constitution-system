package com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.service;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.utils.PageResponse;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.utils.Response;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.tag.AddTagReqVO;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.tag.DeleteTagReqVO;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.tag.FindTagPageListReqVO;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.tag.SearchTagReqVO;

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

