package com.example.questionnaireproject.dto;

import com.example.questionnaireproject.constants.QuestionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserInfoRequest {
    @NotNull
    @JsonProperty("pq_id")
    Integer pqId;

    @NotNull
    @JsonProperty("q_problem_type")
    QuestionType qProblemType;

    @NotNull
    @JsonProperty("AnswerOutput")
    String Answer;

    @NotNull
    @JsonProperty("name")
    String name;
    @NotNull
    @JsonProperty("phone")
    String phone;
    @NotNull
    @Email
    @JsonProperty("email")
    String email;
    @NotNull
    @JsonProperty("q_problem")
    String qProblem;
    @NotNull
    @JsonProperty("age")
    Integer age;
}
