package com.example.questionnaireproject.service;

import com.example.questionnaireproject.dto.QuestionRequest;
import com.example.questionnaireproject.dto.QuestionnaireQueryParam;
import com.example.questionnaireproject.dto.QuestionnaireWrapper;
import com.example.questionnaireproject.model.PrimaryQuestionnaire;
import com.example.questionnaireproject.model.Question;

import java.util.List;

/**
 * ClassName: QuestionnaireService
 * Package: com.example.questionnaireproject.service
 */
public interface QuestionnaireService {
    List<PrimaryQuestionnaire> getPrimaryQuestionnaires(QuestionnaireQueryParam questionnaireQueryParam);

    Integer countQuestionnaires(QuestionnaireQueryParam questionnaireQueryParam);

    void createPrimaryQuestionnaire(PrimaryQuestionnaire primaryQuestionnaire, List<QuestionRequest> list);

    List<Question> getQuestionsByPqId(Integer pqId);

    QuestionnaireWrapper updatePrimaryQuestionnaireAndQuesions(Integer pqId, QuestionnaireWrapper questionnaireWrapper);

    void deletePrimaryQuestionnaires(List<Integer> idList);

    void deleteQuestionByQId(List<Integer> idList);
}
