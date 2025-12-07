package com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.service;

import com.zizhuhuhu.tcmConstitutionSystem.module.common.utils.Response;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.question.FindQuestionPageListReqVO;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.question.DeleteQuestionReqVO;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.question.FindQuestionDetailReqVO;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.question.PublishQuestionReqVO;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.model.vo.question.UpdateQuestionReqVO;

public interface AdminQuestionService {
    Response findQuestionPageList(FindQuestionPageListReqVO findQuestionPageListReqVO);
    Response publishQuestion(PublishQuestionReqVO publishQuestionReqVO);
    Response deleteQuestion(DeleteQuestionReqVO deleteQuestionReqVO);
    Response findQuestionDetail(FindQuestionDetailReqVO findQuestionDetailReqVO);
    Response updateQuestion(UpdateQuestionReqVO updateQuestionReqVO);
}
