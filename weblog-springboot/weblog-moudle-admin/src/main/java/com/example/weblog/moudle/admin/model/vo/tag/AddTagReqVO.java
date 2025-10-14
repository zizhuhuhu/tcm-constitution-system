package com.example.weblog.moudle.admin.model.vo.tag;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "添加分类VO")
public class AddTagReqVO {
    @NotEmpty(message = "标签列表名称不能为空")
    private List<String> tags;
}

