package com.example.questionnaireproject.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class QuestionnaireQueryParam {
    private Date beginDate;
    private Date endedDate;
    private String searchTitle;
    private Integer limit;
    private Integer offset;
}
