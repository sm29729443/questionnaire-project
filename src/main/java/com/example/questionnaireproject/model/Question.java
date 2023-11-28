package com.example.questionnaireproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Question {
    @JsonProperty("q_id")
    Integer qId;
    @JsonProperty("pq_id")
    Integer pqId;
    @JsonProperty("q_problem")
    String qProblem;
    @JsonProperty("q_answer")
    String qAnswer;


}
