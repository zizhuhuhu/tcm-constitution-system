package com.example.weblog.moudle.admin.model.vo.artical;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindArticlePageListRspVO {
    //文章id
    private Long id;
    //文章标题
    private String title;
    //文章封面
    private String cover;
    //发布时间
    private LocalDateTime createTime;

}
