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
@TableName("t_category")
public class CategoryDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String characteristics;
    private String commonSymptoms;
    private String psychologicalTraits;
    private String dietAdvice;
    private String lifestyleAdvice;
    private String exerciseAdvice;
    private String acupointAdvice;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Boolean isDeleted;

}
