package com.example.questionnaireproject.util;

import com.example.questionnaireproject.model.PrimaryQuestionnaire;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Page<T> {
    private Integer limit;
    private Integer offset;
    private Integer total;
    private List<T> results;


}
