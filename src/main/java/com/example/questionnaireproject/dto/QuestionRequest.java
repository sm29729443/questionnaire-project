package com.example.questionnaireproject.dto;

import com.example.questionnaireproject.constants.QuestionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


public class QuestionRequest {
    @JsonProperty("problem")
    private String qProblem;
    @JsonProperty("answer")
    private String qAnswer;
    @JsonProperty("selectType")
    private QuestionType qProblemType;
    @JsonProperty("selectRequired")
    private Boolean qRequireSelect;



    public String getqProblem() {
        return qProblem;
    }

    public void setqProblem(String qProblem) {
        this.qProblem = qProblem;
    }

    public String getqAnswer() {
        return qAnswer;
    }

    public void setqAnswer(String qAnswer) {
        this.qAnswer = qAnswer;
    }

    public QuestionType getqProblemType() {
        return qProblemType;
    }

    public void setqProblemType(QuestionType qProblemType) {
        this.qProblemType = qProblemType;
    }

    public Boolean getqRequireSelect() {
        return qRequireSelect;
    }

    public void setqRequireSelect(Boolean qRequireSelect) {
        this.qRequireSelect = qRequireSelect;
    }
}
