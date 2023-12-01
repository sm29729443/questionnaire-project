package com.example.questionnaireproject.service.impl;

import com.example.questionnaireproject.dao.QuestionnaireDao;
import com.example.questionnaireproject.dto.QuestionRequest;
import com.example.questionnaireproject.dto.QuestionnaireQueryParam;
import com.example.questionnaireproject.dto.QuestionnaireWrapper;
import com.example.questionnaireproject.model.PrimaryQuestionnaire;
import com.example.questionnaireproject.model.Question;
import com.example.questionnaireproject.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {
    @Autowired
    private QuestionnaireDao questionnaireDao;

    @Override
    public List<PrimaryQuestionnaire> getPrimaryQuestionnaires(QuestionnaireQueryParam questionnaireQueryParam) {
        return questionnaireDao.getPrimaryQuestionnaires(questionnaireQueryParam);
    }

    @Override
    public Integer countQuestionnaires(QuestionnaireQueryParam questionnaireQueryParam) {
        return questionnaireDao.countQuestionnaires(questionnaireQueryParam);
    }
    @Transactional
    @Override
    public void createPrimaryQuestionnaire(PrimaryQuestionnaire primaryQuestionnaire,
                                           List<QuestionRequest> list) {
        Integer pqId = questionnaireDao.createPrimaryQuestionnaire(primaryQuestionnaire);
        questionnaireDao.createQuestion(pqId, list);
    }

    @Override
    public List<Question> getQuestionsByPqId(Integer pqId) {
        return questionnaireDao.getQuestionsByPqId(pqId);
    }

    @Transactional
    @Override
    public QuestionnaireWrapper updatePrimaryQuestionnaireAndQuesions(Integer pqId, QuestionnaireWrapper questionnaireWrapper) {
        List<QuestionRequest> createQuestions = new ArrayList<>();
        QuestionRequest questionRequest = new QuestionRequest();
        List<Question> updateQuestions = new ArrayList<Question>();
        for (Question question : questionnaireWrapper.getQuestionList()) {
            if (question.getQId() > 0) {
                updateQuestions.add(question);
            } else {
                questionRequest.setqProblem(question.getQProblem());
                questionRequest.setqAnswer(question.getQAnswer());
                questionRequest.setqRequireSelect(question.getRequireSelect());
                questionRequest.setqProblemType(question.getProblemType());
                createQuestions.add(questionRequest);
            }
        }
        // 更新主問卷
        PrimaryQuestionnaire primaryQuestionnaire = questionnaireDao.updatePrimaryQuestionnare(pqId, questionnaireWrapper.getPrimaryQuestionnaire());
        // 更新子問題
        questionnaireDao.updateQuestion(pqId, updateQuestions);
        // 創毽子問題
        questionnaireDao.createQuestion(pqId, createQuestions);
        // 未來前端可能會需要 get 某些東西，暫定
        return null;
    }

    @Transactional
    @Override
    public void deletePrimaryQuestionnaires(List<Integer> idList) {
        questionnaireDao.deletePrimaryQuestionnaire(idList);
        questionnaireDao.deleteQuestionByPqId(idList);
    }

    @Override
    public void deleteQuestionByQId(List<Integer> idList) {
        questionnaireDao.deleteQuestionByQId(idList);
    }



}
