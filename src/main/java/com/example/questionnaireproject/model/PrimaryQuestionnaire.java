package com.example.questionnaireproject.model;

import com.example.questionnaireproject.constants.QuestionnaireStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Future;
import java.util.Date;


@Setter
@Getter
public class PrimaryQuestionnaire {
    @JsonProperty("pq_id")
    private Integer pqId;
    @JsonProperty("pq_title")
    private String pqTitle;
    @JsonProperty("pq_status")
    private QuestionnaireStatus pqStatus;
    @JsonProperty("pq_created_date")
    private Date pqCreatedDate;
    @JsonProperty("pq_ended_date")
    private Date pqEndedDate;
    @JsonProperty("pq_description")
    private String pqDescription;

}
