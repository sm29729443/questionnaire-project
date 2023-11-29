package com.example.questionnaireproject.dao;

import com.example.questionnaireproject.dto.QuestionRequest;
import com.example.questionnaireproject.dto.QuestionnaireQueryParam;
import com.example.questionnaireproject.model.PrimaryQuestionnaire;

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
}
