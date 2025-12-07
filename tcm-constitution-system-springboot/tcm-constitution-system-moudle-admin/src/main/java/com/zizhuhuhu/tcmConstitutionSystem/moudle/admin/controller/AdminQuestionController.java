package com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.controller;

import com.zizhuhuhu.tcmConstitutionSystem.module.common.utils.Response;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.question.FindQuestionPageListReqVO;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.question.*;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.service.AdminQuestionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/question")
@Api(tags = "Admin问题模块")
public class AdminQuestionController {
    @Autowired
    private AdminQuestionService adminQuestionService;
    @PostMapping("/list")
    public Response findQuestionPageList(@RequestBody @Validated FindQuestionPageListReqVO findQuestionPageListReqVO){
        return adminQuestionService.findQuestionPageList(findQuestionPageListReqVO);
    }
    @PostMapping("/publish")
    public Response publishQuestion(@RequestBody @Validated PublishQuestionReqVO publishQuestionReqVO){
        return adminQuestionService.publishQuestion(publishQuestionReqVO);
    }
    @PostMapping("/delete")
    public Response deleteQuestion(@RequestBody @Validated DeleteQuestionReqVO deleteQuestion){
        return adminQuestionService.deleteQuestion(deleteQuestion);
    }
    @PostMapping("/detail")
    public Response findQuestionDetail(@RequestBody @Validated FindQuestionDetailReqVO findQuestionDetailReqVO){
        return adminQuestionService.findQuestionDetail(findQuestionDetailReqVO);
    }
    @PostMapping("/update")
    public Response updateQuestion(@RequestBody @Validated UpdateQuestionReqVO updateQuestionReqVO){
        return adminQuestionService.updateQuestion(updateQuestionReqVO);
    }

}
