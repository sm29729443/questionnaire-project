package com.example.questionnaireproject.dto;

import com.example.questionnaireproject.constants.QuestionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QuestionRequest {
    @JsonProperty("q_problem")
    Integer qProblem;
    @JsonProperty("q_answer")
    String qAnswer;
    @JsonProperty("q_problem_type")
    QuestionType qProblemType;
}
