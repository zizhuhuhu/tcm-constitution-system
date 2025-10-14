package com.example.weblog.web.model.vo.article;

import com.example.weblog.web.model.vo.category.FindCategoryListRspVO;
import com.example.weblog.web.model.vo.tag.FindTagListRspVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.PrivateKey;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.PrimitiveIterator;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class FindIndexArticlePageListRspVO {
    private Long id;
    private String cover;
    private String title;
    private LocalDate createDate;
    private String summary;
    //文章分类
    private FindCategoryListRspVO category;
    //文章标签
    private List<FindTagListRspVO> tags;


}
