package com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.dos;
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
@TableName("question_category_rel")
public class QuestionCategoryRelDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long questionId;
    private Long categoryId;
}
