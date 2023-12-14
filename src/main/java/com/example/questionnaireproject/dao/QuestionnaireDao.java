package com.example.questionnaireproject.dao;

import com.example.questionnaireproject.dto.QuestionRequest;
import com.example.questionnaireproject.dto.QuestionnaireQueryParam;
import com.example.questionnaireproject.dto.UserInfoRequest;
import com.example.questionnaireproject.model.PrimaryQuestionnaire;
import com.example.questionnaireproject.model.Question;
import com.example.questionnaireproject.model.UserInfoOne;

import java.util.List;

/**
 * ClassName: QuestionnaireDao
 * Package: com.example.questionnaireproject.dao
 */
public interface QuestionnaireDao {
    List<PrimaryQuestionnaire> getPrimaryQuestionnaires(QuestionnaireQueryParam questionnaireQueryParam);

    Integer countQuestionnaires(QuestionnaireQueryParam questionnaireQueryParam);

    Integer createPrimaryQuestionnaire(PrimaryQuestionnaire primaryQuestionnaire);

    void createQuestion(Integer quId, List<QuestionRequest> list);

    List<Question> getQuestionsByPqId(Integer pqId);

    PrimaryQuestionnaire updatePrimaryQuestionnare(Integer pqId, PrimaryQuestionnaire primaryQuestionnaire);

    void updateQuestion(Integer pqId, List<Question> updateQuestions);

    void deletePrimaryQuestionnaire(List<Integer> idList);

    void deleteQuestionByPqId(List<Integer> idList);

    void deleteQuestionByQId(List<Integer> qIdList);

    void createAnswerResults(List<UserInfoRequest> userInfoRequest);

    List<UserInfoOne> getQuestionnaireAnswer(Integer pqId);

    List<PrimaryQuestionnaire> getUserPrimaryQuestionnaires(QuestionnaireQueryParam questionnaireQueryParam);

    Integer countUserQuestionnaires(QuestionnaireQueryParam questionnaireQueryParam);
}
