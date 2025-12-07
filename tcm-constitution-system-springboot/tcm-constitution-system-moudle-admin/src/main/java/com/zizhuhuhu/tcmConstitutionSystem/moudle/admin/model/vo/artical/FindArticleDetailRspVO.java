package com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.artical;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "查询文章详情出参")
public class FindArticleDetailRspVO {
    //文章id
    private Long id;
    //文章标题
    private String title;
    //文章封面
    private String cover;
    //文章内容
    private String content;
    //分类ID
    private Long category;
    //标签id集合
    private List<Long> tagIds;
    //摘要
    private String summary;
}
