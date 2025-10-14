package com.example.weblog.moudle.admin.model.vo.category;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "删除分类VO")
public class DeleteCategoryReqVO {
    @NotNull(message = "分类Id不能为空")
    private Long id;
}
