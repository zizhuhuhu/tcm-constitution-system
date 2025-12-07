package com.example.weblog.moudle.admin.model.vo.artical;

import com.example.weblog.module.common.model.BasePageQuery;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "查看问题分页VO")
public class FindQuestionPageListReqVO extends BasePageQuery {
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
}
