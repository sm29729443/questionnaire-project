package com.example.questionnaireproject.dto;

import com.example.questionnaireproject.model.Question;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class QuestionnaireSessionRequest {
    @NotNull
    private String quName;
    @NotNull
    private String quDescription;
    private Date beginDate;
    private Date endedDate;
    private List<QuestionRequest> questionList;
}
