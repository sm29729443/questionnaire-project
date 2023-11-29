package com.example.questionnaireproject.service;

import com.example.questionnaireproject.dto.QuestionRequest;
import com.example.questionnaireproject.dto.QuestionnaireQueryParam;
import com.example.questionnaireproject.model.PrimaryQuestionnaire;

import java.util.List;

/**
 * ClassName: QuestionnaireService
 * Package: com.example.questionnaireproject.service
 */
public interface QuestionnaireService {
    List<PrimaryQuestionnaire> getPrimaryQuestionnaires(QuestionnaireQueryParam questionnaireQueryParam);

    Integer countQuestionnaires(QuestionnaireQueryParam questionnaireQueryParam);

    void createPrimaryQuestionnaire(PrimaryQuestionnaire primaryQuestionnaire, List<QuestionRequest> list);
}
