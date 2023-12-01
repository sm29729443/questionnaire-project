package com.example.questionnaireproject.dto;

import com.example.questionnaireproject.model.PrimaryQuestionnaire;
import com.example.questionnaireproject.model.Question;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * ClassName: QuestionnaireWrapper
 * Package: com.example.questionnaireproject.dto
 */
@Setter
@Getter
public class QuestionnaireWrapper {
    @NotEmpty
    private PrimaryQuestionnaire primaryQuestionnaire;
    @NotEmpty
    private List<Question> questionList;
}
