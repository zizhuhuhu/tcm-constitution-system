package com.zizhuhuhu.tcmConstitutionSystem.module.common.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SelectRspVO {
    //下拉列表的展示文字
    private String label;
    //获取下拉列表的value值
    private Object value;
}
