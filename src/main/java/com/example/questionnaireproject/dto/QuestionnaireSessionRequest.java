package com.example.questionnaireproject.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class QuestionnaireSessionRequest {
    @NotNull
    private String quTitle;
    @NotNull
    private String quDescription;
    @NotNull
    private Date beginDate;
    @NotNull
    private Date endedDate;
    @NotEmpty
    private List<QuestionRequest> questionList;
}
