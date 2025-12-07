package com.example.weblog.module.common.domain.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("question")
public class QuestionDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String description;
    private Integer sortOrder;
    private Integer questionType;
    private Long constitutionCategoryId;
    private Integer isRequired;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer deleted;
}
