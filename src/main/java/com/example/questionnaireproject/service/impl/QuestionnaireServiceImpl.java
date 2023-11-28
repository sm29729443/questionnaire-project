package com.example.questionnaireproject.service.impl;

import com.example.questionnaireproject.dao.QuestionnaireDao;
import com.example.questionnaireproject.dto.QuestionnaireQueryParam;
import com.example.questionnaireproject.model.PrimaryQuestionnaire;
import com.example.questionnaireproject.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
