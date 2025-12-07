package com.example.weblog.moudle.admin.service;

import com.example.weblog.module.common.utils.Response;
import com.example.weblog.moudle.admin.model.vo.artical.FindQuestionPageListReqVO;
import com.example.weblog.moudle.admin.model.vo.question.DeleteQuestionReqVO;
import com.example.weblog.moudle.admin.model.vo.question.FindQuestionDetailReqVO;
import com.example.weblog.moudle.admin.model.vo.question.PublishQuestionReqVO;
import com.example.weblog.moudle.admin.model.vo.question.UpdateQuestionReqVO;

public interface AdminQuestionService {
    Response findQuestionPageList(FindQuestionPageListReqVO findQuestionPageListReqVO);
    Response publishQuestion(PublishQuestionReqVO publishQuestionReqVO);
    Response deleteQuestion(DeleteQuestionReqVO deleteQuestionReqVO);
    Response findQuestionDetail(FindQuestionDetailReqVO findQuestionDetailReqVO);
    Response updateQuestion(UpdateQuestionReqVO updateQuestionReqVO);
}
